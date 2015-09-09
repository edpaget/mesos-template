(ns {{name}}.components.scheduler-driver
  (:require [clj-mesos.scheduler :as mesos]
            [com.stuartsierra.component :as component]))

(defrecord SchedulerDriver [master scheduler driver user name]
  component/Lifecycle
  (start [component]
    (when-not driver
      (let [driver (mesos/driver (:scheduler scheduler)
                                 {:user user :name name}
                                 master)]
        (mesos/start driver)
        (assoc component :driver driver))))
  (stop [component]
    (when driver
      (mesos/stop driver)
      (assoc component :driver nil))))

(defn new-scheduler-driver
  [master user name]
  (map->SchedulerDriver {:master master :user user :name name}))
