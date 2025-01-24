package io.modacoffee.web.v5;

public class Revision {

    private Revision() {}

    public static String getRevision() {
        return "${moda.revision}";
    }

    public static String getRevisionDisplayName() {
        return "${project.version}";
    }

    public static String getBuildNumber() {
        String buildNumber = new String("${env.BUILD_NUMBER}");
        if (buildNumber != null && !buildNumber.isEmpty() && !buildNumber.contains("env.BUILD_NUMBER")) {
            return buildNumber;
        }

        return "Unknown";
    }

    public static String getScmRevision() {
        String scmRevision = "${buildNumber}";

        if (scmRevision == null || scmRevision.isBlank() || "${project.scm.version}".equals(scmRevision)) {
            scmRevision = new String("${env.SVN_REVISION}");
        }
        if (scmRevision != null && !scmRevision.isEmpty() && !scmRevision.contains("env.SVN_REVISION")) {
            return scmRevision;
        }

        return "Unknown";
    }

    public static String getVersionDisplay() {
        return getRevisionDisplayName() + "-" + getScmRevision() + "-" + getBuildNumber();
    }
}
