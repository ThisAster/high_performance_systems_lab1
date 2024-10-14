/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.example.clinic.app.user.repository;

/**
 *
 * @author thisaster
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clinic.app.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
