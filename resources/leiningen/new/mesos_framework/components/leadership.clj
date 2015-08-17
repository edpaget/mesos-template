(ns {{}}.components.leadership
    (:require [curator.leader :refer (leader-selector)]
              [com.stuartsierra.component :as component]))

(defrecord Leadership [leader-fn loser-fn path curator selector]
  component/Lifecycle
  (start [component]
    (when-not selector
      (let [selector (leader-selector curator path leader-fn loser-fn)]
        (.start selector)
        (assoc component :selector selector))))
  (stop [component]
    (when selector
      (.close selector)
      (assoc component :selector nil))))

(defn new-leadership
  [path]
  (map->Leadership {:path path}))

