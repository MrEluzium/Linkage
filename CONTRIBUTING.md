## Issues

When opening issues, please be sure to include the following information as applicable.

- The exact version of the mod you are running, such as `1.0.0+1.19.2`, and the version of Fabric you are using.
- If your issue is a crash, attach the latest client or server log and the complete crash report as a file. You can
attach these as a file (preferred) or host them on a service such as [GitHub Gist](https://gist.github.com/) or [Pastebin](https://pastebin.com/).
- If your issue is a bug or otherwise unexpected behavior, explain what you expected to happen.
- If your issue only occurs with other mods installed, be sure to specify the names and versions of those mods.

## Pull Requests

It's super awesome to hear you're wishing to contribute to the project! Before you open a pull request, you'll need to
give a quick read to the following guidelines.

### Contributor License Agreement (CLA)

By submitting changes to this repository, you are hereby agreeing that:

- Your contributions will be licensed irrecoverably under the [GNU LGPLv3](https://www.gnu.org/licenses/lgpl-3.0.html).
- Your contributions are of your own work and free of legal restrictions (such as patents and copyrights) or other
issues which would pose issues for inclusion or distribution under the above license.

If you do not agree to these terms, please do not submit contributions to this repository. If you have any questions
about these terms, feel free to get in contact with me through the public Discord server or
through opening an issue.

### Code Style

When contributing source code changes to the project, ensure that you make consistent use of the code style guidelines
used throughout the codebase (which follow pretty closely after the standard Java code style guidelines).

- Use 4 spaces for indentation, not tabs. Avoid lines which exceed 120 characters.
- Use `this` to qualify member and field access.
- Annotate overriding methods with `@Override` so that breaking changes when updating will create hard compile errors.

### Making a Pull Request

Your pull request should include a brief description of the changes it makes and link to any open issues which it
resolves. You should also ensure that your code is well documented where non-trivial and that it follows the
outlined code style guidelines above.