import * as React from "react";
import Shop from "./Shop";
import { useHistory, useParams } from "react-router-dom";
import { useShopPageQuery } from "../generated/graphql";

export default function ShopPage() {
  const history = useHistory();
  const { shopId } = useParams<{ shopId: string }>();

  const { data, loading, error } = useShopPageQuery({
    variables: { shopId },
  });

  if (loading) {
    return <h1>Please wait... Shop is loading...</h1>;
  }
  if (error) {
    console.error("Loading Shop failed: ", error);
    return <h1>Sorry... Loading shop failed</h1>;
  }

  if (!data || !data.shop) {
    return <h1>No data ?</h1>;
  }

  return <Shop shop={data.shop} onBeerClick={(beerId) => history.push(`/beer/${beerId}`)} />;
}
