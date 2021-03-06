(defproject dry-off "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [lambdaisland/uri "1.4.54"]
                 [hickory "0.7.1"]
                 [clj-http "3.10.1"]
                 [com.draines/postal "2.0.3"]]
                 ;[javax.mail/javax.mail-api "1.6.2"]]
  :main ^:skip-aot dry-off.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
