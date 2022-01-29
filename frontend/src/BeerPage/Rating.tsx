import * as React from "react";
import Stars from "../components";
import styles from "./Beer.module.css";
import { SingleBeerFragment } from "../generated/graphql";

interface RatingProps {
  rating: SingleBeerFragment["ratings"][0];
}

const Rating = ({ rating: { author, comment, stars } }: RatingProps) => (
  <div className={styles.Rating}>
    <span className={styles.Author}>{author.name}</span>:{" "}
    <span className={styles.Comment}>
      „{comment}“ <Stars stars={stars} />
    </span>
  </div>
);

export default Rating;
