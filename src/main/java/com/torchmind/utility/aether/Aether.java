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
package com.torchmind.utility.aether;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.util.graph.visitor.PreorderNodeListGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides simplified access to Eclipse Aether.
 *
 * @author {@literal Johannes Donath <johannesd@torchmind.com>}
 */
public class Aether {

	/**
	 * Stores the local repository directory.
	 */
	@Getter
	private File localRepositoryDirectory;

	/**
	 * Stores a list of remote repositories to check.
	 */
	private final List<RemoteRepository> repositories = new ArrayList<> ();

	/**
	 * Stores the currently active repository system.
	 */
	@Getter (AccessLevel.PROTECTED)
	private final RepositorySystem repositorySystem;

	/**
	 * Stores the system session.
	 */
	@Getter (AccessLevel.PROTECTED)
	private RepositorySystemSession session;

	/**
	 * Constructs a new Aether instance.
	 *
	 * @param localRepositoryDirectory The local repository directory.
	 */
	public Aether (File localRepositoryDirectory) {
		// create a new Aether Session
		this.repositorySystem = this.prepareSystem ();

		// update local repository & initialize session
		this.setLocalRepository (localRepositoryDirectory);
	}

	/**
	 * Constructs a new Aether instance.
	 *
	 * @param localRepositoryDirectory The local repository directory.
	 * @param repositories             The remote repository list.
	 */
	public Aether (File localRepositoryDirectory, Collection<RemoteRepository> repositories) {
		this (localRepositoryDirectory);

		this.addRepository (repositories);
	}

	/**
	 * Adds a new remote repository to the list.
	 *
	 * @param repository The repository.
	 */
	public void addRepository (@NonNull RemoteRepository repository) {
		if (this.repositories.contains (repository)) return;
		this.repositories.add (repository);
	}

	/**
	 * Adds a list of new remote repositories to the list.
	 *
	 * @param repositories The repository.
	 */
	public void addRepository (@NonNull Collection<RemoteRepository> repositories) {
		for (RemoteRepository repository : repositories) {
			this.addRepository (repository);
		}
	}

	/**
	 * Clears the repository list.
	 */
	public void clearRepositories () {
		this.repositories.clear ();
	}

	/**
	 * Prepares the repository system implementation.
	 *
	 * @return The system.
	 */
	protected RepositorySystem prepareSystem () {
		DefaultServiceLocator serviceLocator = MavenRepositorySystemUtils.newServiceLocator ();
		serviceLocator.addService (RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
		serviceLocator.addService (TransporterFactory.class, FileTransporterFactory.class);
		serviceLocator.addService (TransporterFactory.class, HttpTransporterFactory.class);

		return serviceLocator.getService (RepositorySystem.class);
	}

	/**
	 * Registers all repositories with a new request.
	 *
	 * @param request The request.
	 */
	protected void registerRepositories (CollectRequest request) {
		for (RemoteRepository repository : this.repositories) request.addRepository (repository);
	}

	/**
	 * Removes a repository.
	 *
	 * @param repository The repository.
	 */
	public void removeRepository (@NonNull RemoteRepository repository) {
		this.repositories.remove (repository);
	}

	/**
	 * Removes a collection of repositories.
	 *
	 * @param repositories The repository collection.
	 */
	public void removeRepository (@NonNull Collection<RemoteRepository> repositories) {
		for (RemoteRepository repository : repositories) {
			this.removeRepository (repository);
		}
	}

	/**
	 * Resolves a dependency.
	 *
	 * @param artifact The artifact.
	 * @param type     The type.
	 * @return The node list generator.
	 * @throws org.eclipse.aether.collection.DependencyCollectionException Occurs if collecting the dependency tree fails.
	 * @throws org.eclipse.aether.resolution.DependencyResolutionException Occurs if resolving the dependency tree fails.
	 */
	public PreorderNodeListGenerator resolve (String artifact, String type) throws DependencyCollectionException, DependencyResolutionException {
		return this.resolve (new Dependency (new DefaultArtifact (artifact), type));
	}

	/**
	 * Resolves a dependency.
	 *
	 * @param artifact The artifact.
	 * @param type     The type.
	 * @return The artifact list.
	 * @throws org.eclipse.aether.collection.DependencyCollectionException Occurs if collecting the dependency tree fails.
	 * @throws org.eclipse.aether.resolution.DependencyResolutionException Occurs if resolving the dependency tree fails.
	 */
	public List<Artifact> resolveArtifacts (Artifact artifact, String type) throws DependencyCollectionException, DependencyResolutionException {
		return this.resolve (artifact, type).getArtifacts (false);
	}

	/**
	 * Resolves a dependency.
	 *
	 * @param artifact The artifact.
	 * @param type     The type.
	 * @return The artifact list.
	 * @throws org.eclipse.aether.collection.DependencyCollectionException Occurs if collecting the dependency tree fails.
	 * @throws org.eclipse.aether.resolution.DependencyResolutionException Occurs if resolving the dependency tree fails.
	 */
	public List<Artifact> resolveArtifacts (String artifact, String type) throws DependencyCollectionException, DependencyResolutionException {
		return this.resolve (artifact, type).getArtifacts (false);
	}

	/**
	 * Resolves a dependency.
	 *
	 * @param artifact The artifact.
	 * @param type     The type.
	 * @return The node list generator.
	 * @throws org.eclipse.aether.collection.DependencyCollectionException Occurs if collecting the dependency tree fails.
	 * @throws org.eclipse.aether.resolution.DependencyResolutionException Occurs if resolving the dependency tree fails.
	 */
	public PreorderNodeListGenerator resolve (Artifact artifact, String type) throws DependencyCollectionException, DependencyResolutionException {
		return this.resolve (new Dependency (artifact, type));
	}

	/**
	 * Resolves a dependency.
	 *
	 * @param dependency The dependency.
	 * @return The node list generator.
	 * @throws org.eclipse.aether.collection.DependencyCollectionException Occurs if collecting the dependency tree fails.
	 * @throws org.eclipse.aether.resolution.DependencyResolutionException Occurs if resolving the dependency tree fails.
	 */
	public PreorderNodeListGenerator resolve (Dependency dependency) throws DependencyCollectionException, DependencyResolutionException {
		CollectRequest collectRequest = new CollectRequest ();
		collectRequest.setRoot (dependency);
		this.registerRepositories (collectRequest);

		DependencyNode node = this.getRepositorySystem ().collectDependencies (this.getSession (), collectRequest).getRoot ();

		DependencyRequest request = new DependencyRequest ();
		request.setRoot (node);

		this.getRepositorySystem ().resolveDependencies (this.getSession (), request);

		PreorderNodeListGenerator generator = new PreorderNodeListGenerator ();
		node.accept (generator);

		return generator;
	}

	/**
	 * Sets the local repository directory.
	 *
	 * @param localRepositoryDirectory The directory.
	 */
	public void setLocalRepository (@NonNull File localRepositoryDirectory) {
		this.localRepositoryDirectory = localRepositoryDirectory;

		this.updateSession ();
	}

	/**
	 * Updates the session instance.
	 */
	protected void updateSession () {
		DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession ();

		LocalRepository repository = new LocalRepository (this.getLocalRepositoryDirectory ());
		session.setLocalRepositoryManager (this.getRepositorySystem ().newLocalRepositoryManager (session, repository));

		this.session = session;
	}
}
