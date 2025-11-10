package dao;

import java.util.List;

/**
 * This interface IDAO (Common Interface for 'Data Access Objects') was created to simplify and generalize the behavior
 * of all the DAO pattern classes to be implemented in this project. The main idea is to reuse the written code, by
 * applying the DRY and KISS principles.
 * @param <E>
 */
public interface IDAO<E> {
    // CRUD (Create-Read-Update-Delete) Interface
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
        List<E> searchAll();

        /**
         * Recovers a specific register from the database using its ID.
         * @param registerToSearch
         * @return
         */
        boolean searchByID(E registerToSearch);

        /**
         * Updates all the information related to an existing register on the database.
         * @param registerToUpdate
         * @return True or false, whether the register was correctly updated or not.
         */
        boolean updateFullRegister(E registerToUpdate);

        /***
         * Updates certain pieces of information related to an existing register on the database. The user is going
         * to be asked about which attributes details are going to be changed.
         * @param registerToUpdate
         * @return
         */
        boolean updatePartialRegister(E registerToUpdate);

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
        we can use whatever information we might need for each casuistic.
     */
}
