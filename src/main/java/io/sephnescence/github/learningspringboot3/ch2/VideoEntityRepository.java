package io.sephnescence.github.learningspringboot3.ch2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface VideoEntityRepository extends JpaRepository<VideoEntity, Long> {
    /*
     This is picked up during component scanning
     Generics glossary
     - <ID> - The generic type of the repository's primary key
     - <T> - The generic type of the repository's immediate domain type (Only appears in one thing so I'm not too
        fussed about not truly understanding this atm)
     - <S> - The generic subtype that extends T (sometimes used for projection types)
     Container types glossary
     - Iterable - An iterable type that does not require all its elements to be fully realised in memory
     - Example - An object used to serve Query by Example
     Will have access to the following from a mixture of JpaRepository functions or from its parents
     - findAll
     - findAll(Example<S>) - No idea what this is as yet
     - findAll(Example<S>, Sort) - No idea what this is as yet
     - findAll(Sort) - No idea what this is as yet
     - findAllById(Iterable<ID>) - No idea what this is as yet
     - findById(ID) - No idea what this is as yet
     - findAll(Pageable) - No idea what this is as yet
     - findAll(Example<S>, Pageable) - No idea what this is as yet
     - findBy(Example<S>)
     - findBy(Example<S>, Pageable)
     - findBy(Example<S>, Sort)
     - findOne(Example<S>)
     - deleteById(ID)
     - deleteAll(Iterable<T>)
     - deleteAllById(Iterable<T>)
     - deleteAllByIdInBatch(Iterable<T>)
     - deleteAllInBatch()
     - save(S)
     - saveAll(Iterable<S>)
     - saveAllAndFlush(Iterable<S>)
     - saveAndFlush(S)
     - count()
     - count(Example<S>)
     - existsById(ID)
    */

    // This is a "custom finder". It doesn't need to be implemented. Thanks, Spring Data!
    List<VideoEntity> findByName (String name);

    // Supposedly these will work too. All Spring Data cares about is that the method starts with "findBy"
    // IgnoreCase and Containing can also be used
    // And/Or/LessThan/GreaterThan/IsStartingWith/StartingWith/StartsWith/IsEndingWith/EndingWith/
    //  EndsWith/IsContaining/Containing/Like/IsNotContaining/NotContaining/NotContains/AllIgnoreCase/
    //  OrderBy<Field>Asc/OrderBy<Field>Desc
    // If your attempt at using this is highlighted with error saying "cannot resolve property", you just
    //  Need to fix up your method name, (or implement the method)
    List<VideoEntity> findByNameIgnoreCase (String name);
    List<VideoEntity> findByNameContaining (String name);
    List<VideoEntity> findByNameContainingIgnoreCase (String name);
    List<VideoEntity> findByNameIgnoreCaseContaining (String name);

    // Actually used in our code
    // List<VideoEntity> findAll (); // Apparently I don't need to declare this
    List<VideoEntity> findByNameContainsOrDescriptionContainsAllIgnoreCase (String name, String description);
    List<VideoEntity> findByNameContainsIgnoreCase (String name);
    List<VideoEntity> findByDescriptionContainsIgnoreCase (String description);

    // We can go crazy
    List<VideoEntity> findByNameStartingWithAndDescriptionContainingAllIgnoreCaseOrderByNameAsc (String name, String description);

    // A note on Containing vs StartsWith vs EndsWith vs Like
    //  In Jakarta Persistence Query Language (JPQL), % is a wildcard.
    //  - %containing%
    //  - StartsWith%
    //  - %EndsWith
    //  - I'm not too sure how Like is supposed to work. I think you provide the wildcards yourself

    // Joins are possible too. Some notes/examples...
    //  (on Person class) findByAddressZipCode will join to Address to search on Zip Code
    // However, if Person has a field called addressZipCode as well, it would use the field before joining
    //  Since the method name would still be (on Person class) findByAddressZipCode, you can tell Spring Data
    //  to favour the join instead by naming the method (on Person class) findByAddress_ZipCode

    /*
    Add security controls to the delete method

    The name of the VideoEntity parameter must match #entity so that it can be de-referenced
     */
    @PreAuthorize("#entity.username == authentication.name OR hasRole('ROLE_ADMIN')")
    @Override
    void delete(@NonNull VideoEntity entity); // The IDE complains if I'm missing @NonNull
}
