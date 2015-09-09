(ns {{name}}.components.leadership
    (:require [curator.leader :refer (leader-selector)]
              [com.stuartsierra.component :as component]))

(defrecord Leadership [leader-fn loser-fn path zookeeper selector]
  component/Lifecycle
  (start [component]
    (when-not selector
      (let [selector (leader-selector (:curator zookeeper) path leader-fn loser-fn)]
        (.start selector)
        (assoc component :selector selector))))
  (stop [component]
    (when selector
      (.close selector)
      (assoc component :selector nil))))

(defn new-leadership
  [leader-fn loser-fn path]
  (map->Leadership {:path path :loser-fn loser-fn :leader-fn leader-fn}))
