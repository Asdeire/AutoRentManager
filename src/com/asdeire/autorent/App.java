package com.asdeire.autorent;

import com.asdeire.autorent.domain.impl.ReviewService;
import com.asdeire.autorent.persistence.entity.impl.User;
import com.asdeire.autorent.persistence.entity.impl.User.Role;
import com.asdeire.autorent.persistence.repository.RepositoryFactory;
import com.asdeire.autorent.persistence.repository.contracts.ReviewRepository;
import com.asdeire.autorent.persistence.repository.contracts.UserRepository;
import java.util.UUID;

public class App {

    public static void main(String[] args) {

        Startup.init();

    }
}