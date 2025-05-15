package dao;

import java.util.List;
import domain.Customer;

public interface IDAO<E> {
    // CRUD (Create-Read-Update-Delete)
        /**
         * Inserts a new register into the database.
         * @param registerToAdd
         * @return true or false, whether the operation was successful or not.
         */
        boolean createRegister(E registerToAdd);


        /**
         * Recovers all the available registers from a given SQL table on the database.
         * @return A List object, filled with the all the entity/domain objects recovered.
         */
        List<Customer> searchAll();

        /**
         * Recovers a specific register from the database using its ID.
         * @param registerToSearch
         * @return
         */
        boolean searchByID(E registerToSearch);

        /**
         * Updates an existing register's information from the database
         * @param registerToUpdate
         * @return True or false, whether the register was correctly updated or not.
         */
        boolean updateRegister(E registerToUpdate);

        /**
         * Deletes a register from the database, based on the register's ID obtained through the object received
         * as a parameter.
         * @param registerToDelete
         * @return True or false, whether the register was effectively removed from the database or not.
         */
        boolean deleteRegister(E registerToDelete);

    /*
        CONCLUSION: In some of this abstract methods, it would be enough to have the ID to search or delete a register
        from the database, but as a good practice, it's good idea to always have passed in the full object, so that
        we can use whatever information we might need.
     */
}
