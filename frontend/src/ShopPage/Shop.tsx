import React from "react";
import styles from "./ShopPage.module.css";
import { ShopFragment } from "../generated/graphql";

type ShopProps = {
  shop: ShopFragment;
  onBeerClick(beerId: string): void;
};

export default function Shop({ shop, onBeerClick }: ShopProps) {
  return (
    <div className={styles.ShopPage}>
      <div className={styles.DescriptionTitle}>
        <h1>{shop.name}</h1>
      </div>
      <div style={{ display: "flex" }}>
        <WhereToFind address={shop.address} />

        <div className={styles.Title}>
          <h1>what's in stock</h1>

          <div className={styles.Beers}>
            {shop.beers.map((b) => (
              <div key={b.id} className={styles.Beer} onClick={() => onBeerClick(b.id)}>
                {b.name}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

type WhereToFindProps = { address: ShopFragment["address"] };
function WhereToFind({ address }: WhereToFindProps) {
  return (
    <div style={{ marginRight: "50px" }}>
      <div className={styles.Title}>
        <h1>where to find</h1>
      </div>
      <div>
        <div className={styles.Address}>
          {address.street}
          <br />
          {address.postalCode} {address.city}
          <br />
          {address.country}
        </div>
      </div>
    </div>
  );
}
