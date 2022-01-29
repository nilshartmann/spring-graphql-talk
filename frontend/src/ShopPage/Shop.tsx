import React from "react";
import { ShopPageQuery_shop, ShopPageQuery_shop_address } from "./querytypes/ShopPageQuery";
import styles from "./ShopPage.module.css";

type ShopProps = {
  shop: ShopPageQuery_shop;
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

type WhereToFindProps = { address: ShopPageQuery_shop_address };
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
