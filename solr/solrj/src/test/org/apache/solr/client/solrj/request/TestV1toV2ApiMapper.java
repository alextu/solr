/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.solr.client.solrj.request;

import org.apache.solr.SolrTestCase;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.common.util.ContentStreamBase;
import org.apache.solr.common.util.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class TestV1toV2ApiMapper extends SolrTestCase {

  @Test
  // commented out on: 24-Dec-2018   @BadApple(bugUrl="https://issues.apache.org/jira/browse/SOLR-12028") // added 20-Sep-2018
  public void testAddReplica() throws IOException {
    CollectionAdminRequest.AddReplica addReplica = CollectionAdminRequest.addReplicaToShard("mycoll", "shard1");
    V2Request v2r = V1toV2ApiMapper.convert(addReplica).build();
    Map<?,?> m = (Map<?,?>) Utils.fromJSON(ContentStreamBase.create(new BinaryRequestWriter(), v2r).getStream());
    assertEquals("/c/mycoll/shards", v2r.getPath());
    assertEquals("shard1", Utils.getObjectByPath(m,true,"/add-replica/shard"));
    assertEquals("NRT", Utils.getObjectByPath(m,true,"/add-replica/type"));
  }
}
