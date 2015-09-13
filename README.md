# mesos-framework

A Leiningen template for building a mesos framework using Clojure.

## Prereqs

Install the following:
- [Leiningen](http://leiningen.org)
- [Vagrant](https://www.vagrantup.com)
- [VirtualBox](https://www.virtualbox.org/)

## Usage

It hasn't been released, so you can't use `lein new mesos-framework` yet.

Instead, here's the current workflow:

1. Clone this repository
2. From the cloned repository's root directory, run Leiningen with the mesos-framework template
3. Start Vagrant from the resulting project directory 
4. Once all nodes have started, SSH into the master node
5. Go to the default project directory and start a Clojure REPL

Step 3 notes:
- Vagrant will download and decompress a base image before starting the first time.  
- You can optionally supply a `MESOS_SLAVES=<count>` environment variable. Default is 2.

```
git clone https://github.com/prasincs/mesos-template.git
cd mesos-template
lein new mesos-framework awesome-mix-vol2 --snapshot
cd awesome-mix-vol2
vagrant up
vagrant ssh master
cd /vagrant
lein repl
```

Now create your own Mesos framework!
```
(go)
```

## Authors

- Ed Paget (@edpaget)
- Prasanna Gautam (@prasincs)

## Contributors

Many thanks to the following folks for helping out with testing.
We appreciate the invaluable feedback provided by the folks here.

- Brent Vukmer (@BrentVukmer)
- Issac Praveen (@icylisper)
- David Greenberg (@dgrnbrg)

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
