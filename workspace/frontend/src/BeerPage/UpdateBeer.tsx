import { useAuthContext } from "AuthContext";
import React from "react";
import styles from "./Form.module.css";
import { isValidAuth } from "types";
import { SingleBeerFragment, useUpdateBeerNameMutation } from "../generated/graphql";

type UpdateBeerProps = {
  beer: SingleBeerFragment;
};

export default function UpdateBeer({ beer }: UpdateBeerProps) {
  const { auth } = useAuthContext();
  const [updateBeerName, { error, data }] = useUpdateBeerNameMutation();

  function onBeerNameChange(newBeerName: string) {
    updateBeerName({
      variables: {
        beerId: beer.id,
        newName: newBeerName,
      },
    });
  }

  if (!isValidAuth(auth) || !(auth.auth.username === "Nils")) {
    return null;
  }

  // we don't care about error handling here
  if (error) console.log("error", error);

  // we don't care about the result here => it's just used for demo purposes
  if (data) console.log("data", data);

  return <UpdateBeerForm beername={beer.name} onNewBeerName={onBeerNameChange} />;
}

type UpdateBeerFormProps = {
  beername: string;
  onNewBeerName(newBeerName: string): void;
};

function UpdateBeerForm({ beername, onNewBeerName }: UpdateBeerFormProps) {
  const [newName, setNewName] = React.useState(beername);
  return (
    <div className={styles.Form}>
      <h3>Your admin and can change the name of {beername}</h3>
      <form>
        <fieldset>
          <div>
            <label>New name:</label> <input type="text" value={newName} onChange={(e) => setNewName(e.currentTarget.value)} />
          </div>
        </fieldset>
        <div>
          <button
            onClick={(e) => {
              e.preventDefault();
              onNewBeerName(newName);
            }}
          >
            Set name for {beername}
          </button>
        </div>
      </form>
    </div>
  );
}
