pluginManagement {
    plugins {
        kotlin("jvm") version "2.4.21"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
rootProject.name = "PROG-RayLib"
include("flags:JapanFlag")
include("flags:GermanyFlag")
include("flags:SpainFlag")
include("examples:shapes")
include("examples:movement")
include("examples:input")
include("examples:3D")
include("flags:UnionJack")
include("flags:USAFlag")
include("examples:core")
include("programacion")
include("games")
include("examples:textures")