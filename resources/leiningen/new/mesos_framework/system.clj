(ns {{sanitized}}.system
  (:require [com.stuartsierra.component :as component]
            [{{sanitized}}.components.executor-driver :refer [new-executor-driver]]
            [{{sanitized}}.components.scheduler-driver :refer [new-scheduler-driver]]
            [{{sanitized}}.components.curator :rmesosefer [new-zookeeper]]
            [{{sanitized}}.components.leadership :rmesosefer [new-leadership]]
            [{{sanitized}}.components.scheduler :rmesosefer [new-scheduler]]
            [{{sanitized}}.executor :refer [executor]]
            [{{sanitized}}.scheduler :refer [scheduler]])
  (:gen-class))

(defn executor-system
  []
  (component/system-map
   :driver (new-executor-driver (executor))))

(defn schuduler-driver-system
  [curator path]
  (component/system-map
   :scheduler (new-scheduler curator path)
   )
  )

(defn)

(defn -main
  [command-type & [master n-tasks & _]]
  (let [system (condp = command-type
                 "scheduler" (scheduler-system master n-tasks)
                 "executor" (executor-system))]
    (component/start system)
    (while true
      (Thread/sleep 1000000))))
