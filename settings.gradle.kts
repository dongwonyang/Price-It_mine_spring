rootProject.name = "crowdSourcing_api"
include("src:main:api")
findProject(":src:main:api")?.name = "api"
include("src:main:api")
findProject(":src:main:api")?.name = "api"
include("src:main:domain")
findProject(":src:main:domain")?.name = "domain"
include("src:main:common")
findProject(":src:main:common")?.name = "common"
