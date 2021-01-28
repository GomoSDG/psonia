(ns psonia.app.money
  (:require ["currency.js" :as currency]))

(defn cents->money
  [cents]
  (-> (currency cents #js {"fromCents" true
                           "separator" ","
                           "symbol" "R"})
      (.format)))

(defn del-money
  [cents]
  )

(defn money
  [cents]
  (js/console.log )
    (let [m (currency cents #js {"fromCents" true
                                 "separator" ","})
          c (-> (.cents m)
                (.toString)
                (.padEnd 2 "0"))
          r (-> (.dollars m)
                (.toString))]
      [:<>
       "R"
       (->> (partition 3 r)
            (map #(apply str %))
            (clojure.string/join ","))
       "." [:small c]]))
