import * as React from "react";
import Stars from "../components";
import styles from "./OverviewPage.module.css";
import { useOverviewPageQuery } from "../generated/graphql";
import { useHistory } from "react-router-dom";

export function OverviewPage() {
  const history = useHistory();
  const { loading, error, data } = useOverviewPageQuery();

  if (error) {
    return <h1>Error while loading Beers</h1>;
  }
  if (loading) {
    return <h1>Please stay tuned - Beers are loading . . .</h1>;
  }

  const { beers } = data!;
  return (
    <div className={styles.BeerOverview}>
      {beers.map((beer: any) => (
        <BeerImage
          key={beer.id}
          name={beer.name}
          stars={beer.averageStars}
          imgUrl={`/assets/beer/${beer.id}-256x256-thumb.jpg`}
          onClick={() => history.push(`/beer/${beer.id}`)}
        />
      ))}
    </div>
  );
}

interface ThumbnailProps {
  imgUrl: string;
  name: string;
  stars: number;
  onClick: () => void;
  active?: boolean;
}

function BeerImage({ imgUrl, name, onClick, stars }: ThumbnailProps) {
  return (
    <div className={styles.BeerImage} onClick={onClick}>
      <img alt={name} src={imgUrl} />
      <span className={styles.Label}>
        <h1>{name}</h1>
        <Stars stars={stars} />
      </span>
    </div>
  );
}
