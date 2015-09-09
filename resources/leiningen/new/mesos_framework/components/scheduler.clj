(ns {{name}}.components.scheduler
  (:require [com.stuartsierra.component :as component]
            [{{name}}.scheduler :as sched]))

(defrecord Scheduler [state path scheduler zookeeper]
  component/Lifecycle
  (start [component]
    (when-not scheduler
      (let [state (or state (atom {}))
            scheduler (sched/scheduler state zookeeper)]
        (assoc component :state state :scheduler scheduler))))
  (stop [component]
    (when scheduler
      (assoc component :state nil :scheduler nil))))

(defn new-scheduler
  [state path]
  (map->Scheduler {:state state :path path}))
