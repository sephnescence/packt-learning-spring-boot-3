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