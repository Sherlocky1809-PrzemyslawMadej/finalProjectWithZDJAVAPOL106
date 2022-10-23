package com.example.conferenceroomreservationsystem.organization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class OrganizationRepositoryTest {

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @ParameterizedTest
    @ArgumentsSource(FindByNameArgumentProvider.class)
    void should_not_return_any_object_when_repo_is_empty_during_find_by_name(List<Organization> organizations,
                                                                             String orgNameToFind,
                                                                             boolean exists)
    {
        // given
        organizations.forEach(o -> testEntityManager.persist(o));
        // when
        Optional<Organization> result = organizationRepository.findByName(orgNameToFind);
        // then
        assertEquals(exists, result.isPresent());
    }

    @ParameterizedTest
    @ArgumentsSource(SortByNameArgumentProvider.class)
    void should_be_returned_if_given_list_of_organizations_when_sort_then_sorted_organization(
            List<Organization> organizationInDb,
            Sort sortBy,
            List<Organization> expectedSortedOrganizationList
    ) {
        // given
        organizationInDb.forEach( o -> {
                testEntityManager.persist(o);
    });
        // when
        List<Organization> results = organizationRepository.findAll(sortBy);

        //then
        for (int i = 0; i < expectedSortedOrganizationList.size(); i++) {
            assertEquals(expectedSortedOrganizationList.get(i).getName(),
                    results.get(i).getName());
        }
    }
}