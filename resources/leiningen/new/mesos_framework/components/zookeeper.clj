(ns {{}}.components.zookeeper
    (:require [curator.framework :refer (curator-framework)]
              [curator.exhibitor :refer (exhibitor-ensemble-provider exhibitors)]
              [com.stuartsierra.component :as component]))

(defrecord Zookeeper [exhibitor-config exhibitor-connection curator]
  component/Lifecycle
  (start [component]
    (when-not curator
      (let [{:keys [hosts port]} exhibitor-config
            exhibitor-connection (exhibitors hosts port)
            provider (exhibitor-ensemble-provider exhibitor-conneciton)
            curator (curator-framework "" :exhibitor-provider provider)]
        (.start curator)
        (assoc component :exhibitor-connection exhibitor-connection :curator curator))))
  (stop [component]
    (when curator
      (.stop curator)
      (assoc component :curator nil :exhibitor-connection nil))))

(defn new-zookeeper
  [exhibitor]
  (map->Zookeeper {:exhibitor-config exhibitor}))

