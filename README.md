Aether Utility
==============
Provides simplified access to maven repositories.

Basic Usage
-----------

```java
Aether aether = new Aether (LOCAL_REPOSITORY);
aether.addRepository ((new RemoteRepository.Builder ("central", "default", "http://repo1.maven.org/maven2/")).build ());

List<Artifact> artifacts = aether.resolveArtifacts ("org.apache.maven.plugins:maven-compiler-plugin:3.2", JavaScopes.RUNTIME);
```

Maven
-----
The utility is currently available via the Torchmind maven repository:

```xml
<repositories>
	<repository>
		<id>torchmind</id>
		<url>http://maven.torchmind.com/snapshot</url>

		<snapshots>
			<enabled>true</enabled>
		</snapshots>
	</repository>
</repositories>

<dependencies>
	<dependency>
		<groupId>com.torchmind.utility</groupId>
		<artifactId>aether</artifactId>
		<version>1.0.0-SNAPSHOT</version>
	</dependency>
</dependencies>
```

Building
--------
The library does not need any special build process. It may be build like any other maven project via ```mvn clean install```.

Acknowledgements
----------------
This library uses Eclipse Aether (licensed under the terms of the Eclipse Public License, 1.0) and maven (licensed under
the terms of the Apache License 2.0).

License
-------

	Copyright 2014 Johannes Donath <http://www.torchmind.com>

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		 http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.