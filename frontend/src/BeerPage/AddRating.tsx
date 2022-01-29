import {useMutation, DataProxy} from "@apollo/client";
import { useAuthContext } from "AuthContext";
import gql from "graphql-tag";
import * as React from "react";
import { assertValidAuth, NewRating } from "types";
import LoginForm from "./LoginForm";
import {
  AddRatingMutation as AddRatingMutationResult,
  AddRatingMutationVariables,
  AddRatingMutation_addRating,
} from "./querytypes/AddRatingMutation";
import { RatingsFragment, RatingsFragment_ratings } from "./querytypes/RatingsFragment";
import AddRatingForm from "./AddRatingForm";

const ADD_RATING_MUTATION = gql`
  mutation AddRatingMutation($input: AddRatingInput!) {
    addRating(ratingInput: $input) {
      id
      beer {
        id
      }
      author {
        name
      }
      comment
      stars
    }
  }
`;

const BEER_RATINGS_FRAGMENT = gql`
  fragment RatingsFragment on Beer {
    id
    ratings {
      id
    }
  }
`;

function mergeRatings(ratings: RatingsFragment_ratings[], newRating: AddRatingMutation_addRating) {
  if (ratings.find((r) => r.id === newRating.id)) {
    // rating already contained in list
    return ratings;
  }

  return [...ratings, newRating];
}

function updateBeerCacheWithNewRating(cache: DataProxy, data: AddRatingMutationResult | null | undefined) {
  if (!data) {
    return;
  }

  const cacheId = `Beer:${data.addRating.beer.id}`;
  const existingBeerInCache = cache.readFragment<RatingsFragment>({
    id: cacheId,
    fragment: BEER_RATINGS_FRAGMENT,
  });

  const newRatings = mergeRatings(existingBeerInCache!.ratings, data.addRating);
  cache.writeFragment({
    id: cacheId,
    fragment: BEER_RATINGS_FRAGMENT,
    data: { ...existingBeerInCache, ratings: newRatings },
  });
}

type AddRatingProps = {
  id: string;
  beerName: string;
};

export default function AddRating({ id, beerName }: AddRatingProps) {
  const { auth, login } = useAuthContext();

  const [addNewRating, { error }] = useMutation<AddRatingMutationResult, AddRatingMutationVariables>(ADD_RATING_MUTATION);

  function handleOnNewRating({ comment, stars }: NewRating) {
    assertValidAuth(auth);
    addNewRating({
      update: (cache, { data }) => updateBeerCacheWithNewRating(cache, data),
      variables: {
        input: {
          userId: auth.auth.userId,
          stars: parseInt(stars),
          comment,
          beerId: id,
        },
      },
    });
  }

  return (
    <>
      <h1>
        ...and what do <em>you</em> think?
      </h1>
      {auth === null || "error" in auth ? (
        <LoginForm login={login} error={auth && auth.error} />
      ) : (
        <AddRatingForm
          beerId={id}
          username={auth.auth.username}
          beerName={beerName}
          error={error ? "" + error : null}
          onNewRating={handleOnNewRating}
        />
      )}
    </>
  );
}
