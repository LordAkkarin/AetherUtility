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
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.List;

/**
 * Provides test cases for the Aether utility.
 * @author {@literal Johannes Donath <johannesd@torchmind.com>}
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
		List<Artifact> artifactList = this.aether.resolveArtifacts ("org.apache.maven.plugins:maven-compiler-plugin:3.2", JavaScopes.RUNTIME);

		Assert.assertEquals (38, artifactList.size ());
	}

	/**
	 * Tests dependency resolving error detection.
	 */
	@Test (expected = DependencyResolutionException.class)
	public void resolveFail () throws DependencyCollectionException, DependencyResolutionException {
		this.aether.resolve ("org.example:example-artifact:1.0-SNAPSHOT", JavaScopes.RUNTIME);
	}
}
