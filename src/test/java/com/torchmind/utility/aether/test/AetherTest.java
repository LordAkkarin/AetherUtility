/*******************************************************************************
 * Copyright 2014 Johannes Donath <johannesd@torchmind.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.torchmind.utility.aether.test;

import com.torchmind.utility.aether.Aether;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.eclipse.aether.util.graph.visitor.PreorderNodeListGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

/**
 * Provides test cases for the Aether utility.
 * @author Johannes Donath <johannesd@torchmind.com>
 */
@RunWith (MockitoJUnitRunner.class)
public class AetherTest {

	/**
	 * Stores the local repository path.
	 */
	public static final File LOCAL_REPOSITORY = new File ("repository");

	/**
	 * Stores the aether instance.
	 */
	private Aether aether;

	/**
	 * Prepares the test cases.
	 */
	@Before
	public void setup () {
		LOCAL_REPOSITORY.mkdirs ();

		this.aether = new Aether (LOCAL_REPOSITORY);
		this.aether.addRepository ((new RemoteRepository.Builder ("central", "default", "http://repo1.maven.org/maven2/")).build ());
	}

	/**
	 * Tests dependency resolving.
	 */
	@Test
	public void resolve () throws DependencyCollectionException, DependencyResolutionException {
		PreorderNodeListGenerator generator = this.aether.resolve ("org.apache.maven.plugins:maven-compiler-plugin:3.2", JavaScopes.RUNTIME);

		Assert.assertEquals ("Generated classpath does not match", "/home/akkarin/develop/aether-utility/repository/org/apache/maven/plugins/maven-compiler-plugin/3.2/maven-compiler-plugin-3.2.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-plugin-api/2.0.9/maven-plugin-api-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-artifact/2.0.9/maven-artifact-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/codehaus/plexus/plexus-utils/1.5.1/plexus-utils-1.5.1.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-core/2.0.9/maven-core-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-settings/2.0.9/maven-settings-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-plugin-parameter-documenter/2.0.9/maven-plugin-parameter-documenter-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/wagon/wagon-webdav/1.0-beta-2/wagon-webdav-1.0-beta-2.jar:/home/akkarin/develop/aether-utility/repository/slide/slide-webdavlib/2.1/slide-webdavlib-2.1.jar:/home/akkarin/develop/aether-utility/repository/commons-httpclient/commons-httpclient/2.0.2/commons-httpclient-2.0.2.jar:/home/akkarin/develop/aether-utility/repository/jdom/jdom/1.0/jdom-1.0.jar:/home/akkarin/develop/aether-utility/repository/de/zeigermann/xml/xml-im-exporter/1.1/xml-im-exporter-1.1.jar:/home/akkarin/develop/aether-utility/repository/commons-logging/commons-logging/1.0.4/commons-logging-1.0.4.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-profile/2.0.9/maven-profile-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-model/2.0.9/maven-model-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/wagon/wagon-provider-api/1.0-beta-2/wagon-provider-api-1.0-beta-2.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-repository-metadata/2.0.9/maven-repository-metadata-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-error-diagnostics/2.0.9/maven-error-diagnostics-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-project/2.0.9/maven-project-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-plugin-registry/2.0.9/maven-plugin-registry-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-plugin-descriptor/2.0.9/maven-plugin-descriptor-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-artifact-manager/2.0.9/maven-artifact-manager-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-monitor/2.0.9/maven-monitor-2.0.9.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/maven-toolchain/1.0/maven-toolchain-1.0.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/shared/maven-shared-utils/0.1/maven-shared-utils-0.1.jar:/home/akkarin/develop/aether-utility/repository/com/google/code/findbugs/jsr305/2.0.1/jsr305-2.0.1.jar:/home/akkarin/develop/aether-utility/repository/org/apache/maven/shared/maven-shared-incremental/1.1/maven-shared-incremental-1.1.jar:/home/akkarin/develop/aether-utility/repository/org/codehaus/plexus/plexus-component-annotations/1.5.5/plexus-component-annotations-1.5.5.jar:/home/akkarin/develop/aether-utility/repository/org/codehaus/plexus/plexus-compiler-api/2.4/plexus-compiler-api-2.4.jar:/home/akkarin/develop/aether-utility/repository/org/codehaus/plexus/plexus-compiler-manager/2.4/plexus-compiler-manager-2.4.jar:/home/akkarin/develop/aether-utility/repository/org/codehaus/plexus/plexus-compiler-javac/2.4/plexus-compiler-javac-2.4.jar:/home/akkarin/develop/aether-utility/repository/org/codehaus/plexus/plexus-container-default/1.5.5/plexus-container-default-1.5.5.jar:/home/akkarin/develop/aether-utility/repository/org/codehaus/plexus/plexus-classworlds/2.2.2/plexus-classworlds-2.2.2.jar:/home/akkarin/develop/aether-utility/repository/org/apache/xbean/xbean-reflect/3.4/xbean-reflect-3.4.jar:/home/akkarin/develop/aether-utility/repository/log4j/log4j/1.2.12/log4j-1.2.12.jar:/home/akkarin/develop/aether-utility/repository/commons-logging/commons-logging-api/1.1/commons-logging-api-1.1.jar:/home/akkarin/develop/aether-utility/repository/com/google/collections/google-collections/1.0/google-collections-1.0.jar:/home/akkarin/develop/aether-utility/repository/junit/junit/3.8.2/junit-3.8.2.jar", generator.getClassPath ());
	}

	/**
	 * Tests dependency resolving error detection.
	 */
	@Test (expected = DependencyResolutionException.class)
	public void resolveFail () throws DependencyCollectionException, DependencyResolutionException {
		this.aether.resolve ("org.example:example-artifact:1.0-SNAPSHOT", JavaScopes.RUNTIME);
	}
}
