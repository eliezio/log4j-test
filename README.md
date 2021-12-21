## Branches

| Branch     | Test                                                           |
|------------|----------------------------------------------------------------|
| master     | Both `log4j-to-slf4j` and `log4j-core` are in the RT classpath |
| only-log4j | Only `log4j-core` on the RT classpath                          |

## How to run

First, build the application binary with:

```shell
$ ./gradlew installDist
```

Then run the application with no arguments to see the instructions:

```shell
$ ./build/install/log4j-test/bin/log4j-test
```
