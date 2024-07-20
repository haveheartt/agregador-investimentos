{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  name = "java-shell";
  nativeBuildInputs = with pkgs; [
    maven
    openjdk22
  ];
  shellHook = ''
        export JAVA_HOME=${pkgs.openjdk22}
      '';
}