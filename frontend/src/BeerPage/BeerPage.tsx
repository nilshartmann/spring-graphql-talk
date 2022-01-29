import Beer from "BeerPage/Beer";
import React from "react";
import { useHistory, useParams } from "react-router-dom";
import { assertValidData } from "types";
import UpdateBeer from "./UpdateBeer";
import { NewRatingDocument, NewRatingSubscription, useBeerPageQuery } from "../generated/graphql";

export default function BeerPage() {
  const history = useHistory();
  const { beerId } = useParams<{ beerId: string }>();
  const { loading, error, data, subscribeToMore } = useBeerPageQuery({
    variables: { beerId },
    fetchPolicy: "cache-and-network",
  });

  if (loading) {
    return <h1>Loading...</h1>;
  }
  if (error) {
    console.error(error);
    return <h1>Error! {error.message}</h1>;
  }

  assertValidData(data);

  const { beer } = data;

  if (!beer) {
    //
    return <h1>Beer Not found</h1>;
  }

  return (
    <div>
      <Beer
        beer={beer}
        onShopClicked={(newShopId) => history.push(`/shop/${newShopId}`)}
        subscribeToNewData={() =>
          subscribeToMore<NewRatingSubscription>({
            document: NewRatingDocument,
            variables: { beerId },
            updateQuery: (prev, { subscriptionData }) => {
              if (!prev.beer) {
                return prev;
              }
              return {
                beer: {
                  ...prev.beer,
                  ratings: [...prev.beer.ratings, subscriptionData.data.rating],
                },
              };
            },
          })
        }
      />
      <UpdateBeer beer={beer} />
    </div>
  );
}
