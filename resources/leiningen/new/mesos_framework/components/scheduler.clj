(ns {{name}}.components.scheduler
  (:require [com.stuartsierra.component :as component]
            [{{name}}.scheduler :as sched]))

(defrecord Scheduler [state scheduler zookeeper]
  component/Lifecycle
  (start [component]
    (when-not scheduler
      (let [state (atom {}]
            scheduler (sched/scheduler state zookeeper)]
        (assoc component :state state :scheduler scheduler))))
  (stop [component]
    (when scheduler
      (assoc component :state nil :scheduler nil))))

(defn new-scheduler
  []
  (Scheduler.))

