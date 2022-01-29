import * as React from "react";
import { NewRating } from "../types";
import styles from "./Form.module.css";

type RatingFormProps = {
  beerName: string;
  beerId: string;
  username: string;
  error?: string | null;
  onNewRating: (rating: NewRating) => void;
};

export default function RatingForm({ beerName, username, error, onNewRating }: RatingFormProps) {
  const [comment, setComment] = React.useState("");
  const [stars, setStars] = React.useState("");

  const onCommentChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setComment(e.currentTarget.value);
  };

  const onStarsChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setStars(e.currentTarget.value);
  };

  const onLeaveRatingClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    onNewRating({ comment, stars });
    setComment("");
    setStars("");
  };

  const buttonEnabled = !!stars && !!comment;

  return (
    <div className={styles.Form}>
      <form>
        <fieldset>
          <div>
            <label>Your name:</label> <input type="text" value={username} readOnly />
          </div>
          <div>
            <label>Your rating (1-5):</label> <input type="number" min="1" max="5" value={stars} onChange={onStarsChange} />
          </div>
          <div>
            <label>Your comment:</label> <input type="text" value={comment} onChange={onCommentChange} />
          </div>
          <div>
            <button disabled={!buttonEnabled} onClick={onLeaveRatingClick}>
              Leave rating for {beerName}
            </button>
          </div>
          {error && (
            <div>
              <b>Could not add rating:</b> {error}
            </div>
          )}
        </fieldset>
      </form>
    </div>
  );
}
