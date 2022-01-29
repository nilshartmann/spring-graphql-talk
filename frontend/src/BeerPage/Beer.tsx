import React from "react";
import AddRating from "./AddRating";
import styles from "./Beer.module.css";
import { BeerPageQuery_beer as BeerData, BeerPageQuery_beer_shops as ShopData } from "./querytypes/BeerPageQuery";
import Rating from "./Rating";

type ShopProps = {
  shop: ShopData;
  onShopClicked: (newCurrentShopId: string) => void;
};

const Shop = ({ shop: { id, name }, onShopClicked }: ShopProps) => (
  <div className={styles.Shop} onClick={() => onShopClicked(id)}>
    <span className={styles.Name}>{name}</span>
  </div>
);

export type SubscribeToMoreFnResult = () => void;
type BeerProps = {
  beer: BeerData;
  subscribeToNewData(): SubscribeToMoreFnResult;
  onShopClicked: (newCurrentShopId: string) => void;
};

export default function Beer(props: BeerProps) {
  const {
    beer: { id, name, price, ratings, shops },
    onShopClicked,
    subscribeToNewData,
  } = props;

  React.useEffect(() => {
    const unsubscribeFromNewRatings = subscribeToNewData();
    return unsubscribeFromNewRatings;
  }, [id, subscribeToNewData]);

  return (
    <div className={styles.Beer}>
      <div className={styles.DescriptionTitle}>
        <h1>{name}</h1>
        <h3>{price}</h3>
      </div>
      <div className={styles.Description}>
        <div className={styles.Img}>
          <img alt={name} src={`/assets/beer/${id}.jpg`} />
        </div>
        <div>
          <WhereToBuy shops={shops} onShopClicked={onShopClicked} />
          <div className={styles.Ratings}>
            <h1>What customers say:</h1>
            {ratings.map((rating) => (
              <Rating key={rating.id} rating={rating} />
            ))}
          </div>

          <AddRating id={id} beerName={name} />
        </div>
      </div>
    </div>
  );
}

type WhereToBuyProps = {
  shops: ShopData[];
  onShopClicked: (newCurrentShopId: string) => void;
};

function WhereToBuy({ shops, onShopClicked }: WhereToBuyProps) {
  return (
    <div className={styles.Shops}>
      <h1>Where to buy:</h1>
      {shops.map((shop, ix) => (
        <React.Fragment key={shop.id}>
          <Shop shop={shop} onShopClicked={onShopClicked} />
          {ix < shops.length - 1 ? " | " : null}
        </React.Fragment>
      ))}
    </div>
  );
}
