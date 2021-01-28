(ns psonia.app.money
  (:import
   (goog.i18n NumberFormat)
   (goog.i18n.NumberFormat Format))
  (:require ["currency.js" :as currency]
            [goog.i18n.NumberFormat.Format]))

(def nff
  (NumberFormat. Format/DECIMAL))

(defn cents->money
  [cents]
  (-> (currency cents #js {"fromCents" true
                           "separator" ","
                           "symbol" "R"})
      (.format)))

(defn money
  [cents]
    (let [m (currency cents #js {"fromCents" true
                                 "separator" ","})
          c (-> (.cents m)
                (.toString)
                (.padEnd 2 "0"))
          r (->> (.dollars m)
                (.toString)
                (.format nff))]
      [:span "R" r "." [:small c]]))
