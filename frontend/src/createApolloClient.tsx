
import {
	ApolloClient, ApolloLink, FetchResult, HttpLink,
	InMemoryCache, Observable, Operation, split,
} from "@apollo/client";

import { getAuthToken, setAuthToken } from "./AuthContext";
import { print, GraphQLError } from 'graphql';
import { createClient, ClientOptions, Client } from 'graphql-ws';
import {getMainDefinition} from "@apollo/client/utilities";
import {onError} from "@apollo/client/link/error";
import {setContext} from "@apollo/client/link/context";
import {WebSocketLink} from "@apollo/client/link/ws";

const isLocalDev = window.location.hostname === "localhost";

class GraphQLWsWebSocketLink extends ApolloLink {
	private client: Client;

	constructor(options: ClientOptions) {
		super();
		this.client = createClient(options);
	}

	public request(operation: Operation): Observable<FetchResult> {
		return new Observable((sink) => {
			return this.client.subscribe<FetchResult>(
				{ ...operation, query: print(operation.query) },
				{
					next: sink.next.bind(sink),
					complete: sink.complete.bind(sink),
					error: (err) => {
						if (err instanceof Error) {
							return sink.error(err);
						}

						if (err instanceof CloseEvent) {
							return sink.error(
								// reason will be available on clean closes
								new Error(
									`Socket closed with event ${err.code} ${err.reason || ''}`,
								),
							);
						}

						return sink.error(
							new Error(
								(err as GraphQLError[])
									.map(({ message }) => message)
									.join(', '),
							),
						);
					},
				},
			);
		});
	}
}

export default function createApolloClient() {
  const httpLink = new HttpLink({
    uri: isLocalDev ? "http://localhost:9000/graphql" : "/graphql",
    credentials: "include",
  });

	let lib = process.env.REACT_APP_GRAPHQL_LIB;

	console.log("USING SUBSCRIPTION LIB " + lib)

	const wsLink = lib === "graphql-ws" ? new GraphQLWsWebSocketLink({
		url:isLocalDev ? "ws://localhost:9000/subscriptions" : `ws://${window.location.host}/subscriptions`,
		connectionParams: () => {
			return {}
		},
	}): new WebSocketLink({
		uri: isLocalDev ? "ws://localhost:9000/subscriptions" : `ws://${window.location.host}/subscriptions`,
		options: {
			reconnect: true,
		},
	});


  // using the ability to split links, you can send data to each link
  // depending on what kind of operation is being sent
  const remoteLink = split(
    // split based on operation type
    ({ query }) => {
      const def = getMainDefinition(query);
      return def.kind === "OperationDefinition" && def.operation === "subscription";
    },
    wsLink,
    httpLink
  );

  const errorLink = onError(({ graphQLErrors, networkError }) => {
    if (graphQLErrors) {
      graphQLErrors.map(({ message, locations, path }) =>
        console.log(`[GraphQL error]: Message: ${message}, Location: ${locations}, Path: ${path}`)
      );
    }
    if (networkError) {
      // no real way to interpret return value, so remove token in all cases
      setAuthToken(null);
      console.log(`[Network error]: ${networkError}`);
    }
  });

  const authLink = setContext((_, { headers }) => {
    const token = getAuthToken();
    if (token) {
      return {
        headers: {
          ...headers,
          authorization: `Bearer ${token}`,
        },
      };
    }
    return headers;
  });

  const client = new ApolloClient({
    link: ApolloLink.from([errorLink, authLink, remoteLink]),
    cache: new InMemoryCache(),
  });

  return client;
}
