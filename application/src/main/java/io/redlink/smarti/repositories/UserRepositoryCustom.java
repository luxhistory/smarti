/*
 * Copyright 2017 Redlink GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.redlink.smarti.repositories;

import io.redlink.smarti.model.SmartiUser;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface UserRepositoryCustom {
    SmartiUser create(SmartiUser user);

    SmartiUser removeClient(String login, ObjectId id);

    SmartiUser addClient(String login, ObjectId id);

    List<SmartiUser> findAllWithFilter(String filter);

    SmartiUser updateProfile(String login, Map<String, String> profile);
}
