![A weapon you held...](./images/a-weapon-you-held-and-didnt-know-how-to-use-75916.jpeg)

## Introduction

This project intends to debunk two common misbeliefs regarding the impact of the recently discovered Log4j 2.x
vulnerabilities on Java applications for two typical scenarios, as reported by the following CVE records:
- [CVE-2021-44228](https://www.cve.org/CVERecord?id=CVE-2021-44228)
- [CVE-2021-45046](https://www.cve.org/CVERecord?id=CVE-2021-45046)
- [CVE-2021-45105](https://www.cve.org/CVERecord?id=CVE-2021-45105)

The first two, famously nicknamed Log4Shell, enable Remote-Code-Execution, while the last one allows a Denial-of-Service. 

### Misbelief #1: All Log4j modules from 2.0 to 2.16 were impacted by at least one of these vulnerabilities

Unfortunately, only the description of first CVE explicitly says that only the `log4j-core` module was affected but the other two are vague on this impact.
The official [Apache Log4j Security Vulnerabilities](https://logging.apache.org/log4j/2.x/security.html) page,
more accurately states that only the `log4j-core` was impacted by the 3 recent vulnerabilities.
Therefore, we can safely claim that:

> Your application is immune to those three vulnerabilities if it does not include the `log4j-core` module in the runtime classpath.

### Misbelief #2: If log4-core older than 2.17 is present in the classpath, then my application is _necessarily_ vulnerable 

This one is more subtle. Many applications are based on Spring Boot that by default uses Logback as its logger, but also includes some Log4j modules to bridge the Log4J API to SLF4J/Logback, namely:
- `log4j-to-slf4j`
- `log4j-api`

Not rarely, the `log4j-core` is also indirectly included on the runtime classpath by some application dependency. At the end, we have two available implementations (aka "providers") that can be used when the Log4j API is used directly.

As per implemented on [Log4j's LogManager initialization code](https://github.com/apache/logging-log4j2/blob/a19ef9bceeaad862cfc0b50394a7f791d5e17b8c/log4j-api/src/main/java/org/apache/logging/log4j/LogManager.java#L115), only one provider is used, the one with higher priority. Luckily, `log4j-to-slf4j` has higher priority ([15](https://github.com/apache/logging-log4j2/blob/be881e503e14b267fb8a8f94b6d15eddba7ed8c4/log4j-to-slf4j/src/main/java/org/apache/logging/slf4j/SLF4JProvider.java#L26)) than `log4j-core` ([10](https://github.com/apache/logging-log4j2/blob/be881e503e14b267fb8a8f94b6d15eddba7ed8c4/log4j-core/src/main/java/org/apache/logging/log4j/core/impl/Log4jProvider.java#L26)), ultimately renegating any vulnerable `log4j-core` module into an "inert pathogen".
Again, we can conclude that:

> Your application is also immune if it contains *both* `log4j-to-slf4j` and `log4j-core` in the runtime classpath.

## Branches

| Branch     | Test                                                           |
|------------|----------------------------------------------------------------|
| master     | Both `log4j-to-slf4j` and `log4j-core` are in the RT classpath |
| only-log4j | Only `log4j-core` on the RT classpath                          |

For all branches, we're using the old, unpatched, Log4j v2.14.1. 

## How to run

First, build the application binary with:

```shell
$ ./gradlew installDist
```

Then run the application with no arguments to see the instructions:

```shell
$ ./build/install/log4j-test/bin/log4j-test
```
