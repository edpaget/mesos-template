# mesos-framework

A Leiningen template for building a mesos framework using Clojure.

## Usage

It hasn't been released yet, so you can't just use `lein new mesos-framework` yet.
Instead here's the workflow

```
lein new mesos-framework awesome-mix-vol2 --snapshot
```

It comes with Vagrant already setup. So then you can just run
from `awesome-mix-vol2` directory.


```
vagrant up
```

Optionally, you can supply a `MESOS_SLAVES=<count>` environment variable to
create the desired number of slaves too. Default is 2.

Once the servers are up, ssh into the master node.

```
vagrant ssh master
```

The project is mounted conveniently at the `/vagrant` directory.

```
cd /vagrant
lein repl
```

Now you're off to start your very own mesos framework!

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
