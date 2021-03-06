import { Epic, ofType } from "redux-observable"
import { filter, map, switchMap } from "rxjs/operators"

import { actions } from "store"
import {
  BootstrapAction,
  ConfigurationShape,
  ConsoleAT,
  StoreAction,
  StoreShape,
} from "types"
import { fromFetch } from "utils"

export const getConfiguration: Epic<StoreAction, StoreAction, StoreShape> = (
  action$,
) =>
  action$.pipe(
    ofType<StoreAction, BootstrapAction>(ConsoleAT.BOOTSTRAP),
    switchMap(() =>
      fromFetch<ConfigurationShape>("assets/console-configuration.json").pipe(
        map((response) => {
          if (!response.error) {
            return actions.console.setConfiguration(response.data)
          }
        }),
        filter((a): a is StoreAction => !!a),
      ),
    ),
  )

export default [getConfiguration]
