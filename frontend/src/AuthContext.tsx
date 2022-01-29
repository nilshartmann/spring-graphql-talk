import { useMutation } from "@apollo/client";
import gql from "graphql-tag";
import { LoginMutation, LoginMutationVariables } from "querytypes/LoginMutation";
import * as React from "react";
import { assertValidData, AuthErrorState, AuthInfoState } from "types";

interface IAuthContext {
  auth: AuthInfoState | AuthErrorState | null;
  login(userId: string): void;
}

const AuthContext = React.createContext<IAuthContext>({
  auth: null,
  login() {},
});

const getAuthToken = () => sessionStorage.getItem("auth-token");
const setAuthToken = (authToken: string | null) =>
  authToken ? sessionStorage.setItem("auth-token", authToken) : sessionStorage.removeItem("auth-token");

interface AuthProviderState {
  auth: AuthInfoState | AuthErrorState | null;
}

const LOGIN_MUTATION = gql`
  mutation LoginMutation($username: String!) {
    login(username: $username) {
      authentication {
        userId
        username
        authToken
      }
      error
    }
  }
`;

function useLoginMutation() {
  const [login] = useMutation<LoginMutation, LoginMutationVariables>(LOGIN_MUTATION);

  const doLogin = React.useCallback(
    (loginId: string) => {
      return login({
        variables: {
          username: loginId,
        },
      });
    },
    [login]
  );

  return doLogin;
}

type AuthProviderProps = {
  children: React.ReactElement;
};

function AuthProvider({ children }: AuthProviderProps) {
  const loginMutation = useLoginMutation();
  const [authState, setAuthState] = React.useState<AuthProviderState>({
    auth: null,
  });

  const login = React.useCallback(
    async function login(loginId: string) {
      setAuthToken(null);

      const loginResult = await loginMutation(loginId);
      if (!loginResult.data) {
        setAuthState({
          auth: {
            error: `Could not authenticate (${loginResult.errors})`,
          },
        });
        return;
      }

      const { error, authentication } = loginResult.data.login;

      if (error) {
        setAuthState({
          auth: {
            error: `Could not authenticate (${error})`,
          },
        });
        return;
      }

      assertValidData(authentication);

      setAuthToken(authentication.authToken);

      setAuthState({
        auth: {
          auth: {
            userId: authentication.userId,
            username: authentication.username,
          },
        },
      });
    },
    [loginMutation]
  );

  return (
    <AuthContext.Provider
      value={{
        auth: authState.auth,
        login: login,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

function useAuthContext() {
  const authContext = React.useContext(AuthContext);
  return authContext;
}

export { AuthProvider, useAuthContext, setAuthToken, getAuthToken };
