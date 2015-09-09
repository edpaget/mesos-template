(ns {{name}}.system
    (:require [com.stuartsierra.component :as component]
              [{{name}}.components.executor-driver :refer [new-executor-driver]]
              [{{name}}.components.scheduler-driver :refer [new-scheduler-driver]]
              [{{name}}.components.exhibitor :refer [new-zookeeper]]
              [{{name}}.components.leadership :refer [new-leadership]]
              [{{name}}.components.scheduler :refer [new-scheduler]]
              [{{name}}.executor :refer [executor]]
              [{{name}}.scheduler :refer [scheduler]])
    (:gen-class))

(defn executor-system
  [{:keys [exhibitor zk-path]}]
  (component/system-map
   :zookeeper (new-zookeeper exhibitor)
   :driver (component/using
            (new-executor-driver (executor))
            [:zookeeper])))

(defn scheduler-system
  [{:keys [master exhibitor zk-path name user]
    :or {master "zk://localhost:2181/mesos" name "{{sanitized}}"}}]
  (let [scheduler-state (atom {:leader false})
        leader-fn (fn [_ _] (while true
                              (swap! scheduler-state assoc :leader true)
                              (Thread/sleep (* 50 10000))))
        loser-fn (fn [_ _] (swap! scheduler-state assoc :leader true))]
    (component/system-map
     :zookeeper (new-zookeeper exhibitor)
     :leadership (component/using
                  (new-scheduler leader-fn loser-fn path)
                  [:zookeeper])
     :scheduler (component/using
                 (new-scheduler path)
                 [:zookeeper])
     :driver (component/using
              (new-scheduler-driver master user name)
              [:scheduler]))))

(defn -main
  [command-type & [master exhibitor-host exhibitor-port zookeeper-path]]
  (let [system (condp = command-type
                 "scheduler" (scheduler-system {:master master
                                                :exhibitor {:hosts [exhibitor-host]
                                                            :port exhibitor-port
                                                            :backup "localhost:2181"}
                                                :zk-path zookeeper-path})
                 "executor" (executor-system))]
    (component/start system)
    (while true
      (Thread/sleep 1000000))))
