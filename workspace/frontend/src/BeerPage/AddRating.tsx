import { DataProxy } from "@apollo/client";
import { useAuthContext } from "AuthContext";
import * as React from "react";
import { assertValidAuth, NewRating } from "types";
import LoginForm from "./LoginForm";
import AddRatingForm from "./AddRatingForm";
import { AddRatingMutation, BeerRatingsFragment, BeerRatingsFragmentDoc, useAddRatingMutation } from "../generated/graphql";

function mergeRatings(ratings: BeerRatingsFragment["ratings"], newRating: AddRatingMutation["addRating"]) {
  if (ratings.find((r) => r.id === newRating.id)) {
    // rating already contained in list
    return ratings;
  }

  return [...ratings, newRating];
}

function updateBeerCacheWithNewRating(cache: DataProxy, data: AddRatingMutation | null | undefined) {
  if (!data) {
    return;
  }

  const cacheId = `Beer:${data.addRating.beer.id}`;
  const existingBeerInCache = cache.readFragment<BeerRatingsFragment>({
    id: cacheId,
    fragment: BeerRatingsFragmentDoc,
  });

  const newRatings = mergeRatings(existingBeerInCache!.ratings, data.addRating);
  cache.writeFragment({
    id: cacheId,
    fragment: BeerRatingsFragmentDoc,
    data: { ...existingBeerInCache, ratings: newRatings },
  });
}

type AddRatingProps = {
  id: string;
  beerName: string;
};

export default function AddRating({ id, beerName }: AddRatingProps) {
  const { auth, login } = useAuthContext();

  const [addNewRating, { error }] = useAddRatingMutation();

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
