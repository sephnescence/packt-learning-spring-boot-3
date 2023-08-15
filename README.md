I'm following through Learning Spring Boot 3.0 Third Edition

- Essentially we start with https://start.spring.io/. I've no experience with Gradle so I always us Maven. I've only ever used Java at present, and 17 is the latest LTS from my understanding. 20 is more bleeding edge, but another LTS should be releasing at the end of the year
- Once you've configured the base details, you'll want to include the Spring Web starter. There are around 50 in total according to the book, but I haven't had the chance to see what the other ones are just yet
- Apparently if you're using Spring Boot, managing dependencies will be a lot easier as Spring Boot will manage security updates and the likes for you, so a bit cleaner that NPM and constantly having to deal with advisories
- TIL if you want to go grab another starter that you didn't get the first time around, you can just configure your project again on https://start.spring.io/ and include the new dependency. Instead of clicking "Generate", just click "Explore", select `pom.xml`, and copy the dependency you require
  - e.g. when we were instructed to add Mustache after generating a project with only Spring Web
    ```
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-mustache</artifactId>
    </dependency>
    ```
- Note that usages of `@Controller` and `@Service` are related to Spring Boot's Component Scanning
- Packt have a repository to look at their versions by the end of the chapter. e.g. https://github.com/PacktPublishing/Learning-Spring-Boot-3.0-Third-Edition/tree/main/ch2

- Jackson - JSON library
- @RestController annotation
- @RequestBody annotation allows for Jackson to decode the income JSON body request

- Adding support for NodeJS
- Add an additional `<plugin>` entry in `pom.xml`. This is apparently called a **coordinate**
  ```xml
  <plugin>
      <groupId>com.github.eirslett</groupId>
      <artifactId>frontend-maven-plugin</artifactId>
      <version>1.12.1</version> <!-- 1.13.4 is the latest -->
      <executions>
          <execution>
              <goals>
                  <goal>install-node-and-npm</goal>
              </goals>
          </execution>
      </executions>
      <configuration>
          <nodeVersion>v16.14.2</nodeVersion> <!-- 18.17.1 is the latest -->
      </configuration>
  </plugin>
  ```
- This will download the `https://github.com/eirslett/frontend-maven-plugin` repo.
- Node 20 is already out now, and there's been a recent surge in activity for the package, but I'll download 1.12.1 still to be safe
- Spring Boot will bundle anything in `src/main/resources/static`, meaning the bundle results should be pointed there, and they should be available to the web page, during Maven's "generate-resources" phase. This is run with `./mvnw generate-resources`
- Double check that the `node` and `node_modules` directories that was just created was not added to source control, or appears as new files with `git status`. If they do appear, be sure to ignore them
- You may also need to run npm install to create the `node_modules` folder
- There are 31 phases that maven plugins can run in. This book referred to "clean" as a "Maven clean cycle", but I'm guessing they meant "phase"?
- At least, this is what intelliJ went to autocomplete `<phase></phase>` with then adding the `<execution>` for running npx
  - clean
  - compile
  - deploy
  - generate-resources
  - generate-sources
  - generate-test-resources
  - generate-test-sources
  - initialize
  - install
  - integration-test
  - none
  - package
  - post-clean
  - post-integration-test
  - post-site
  - pre-clean
  - pre-integration-test
  - pre-site
  - prepare-package
  - process-classes
  - process-resources
  - process-sources
  - process-test-classes
  - process-test-resources
  - process-test-sources
  - site
  - site-deploy
  - test
  - test-compile
  - validate
  - verify
- React has now been added! You can access it via heading to http://localhost:8080/react
  - Note that you must first run `./mvnw generate-resources` and whenever you make changes. Hot module reloading doesn't appear to be a thing just yet BTTODO