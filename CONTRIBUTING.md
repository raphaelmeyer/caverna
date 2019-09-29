First off, thanks for taking the time to contribute!

The following is a set of guidelines for contributing to the caverna scoring app.
These are mostly guidelines, not rules.
Use your best judgment, and feel free to propose changes to this document in a pull request.

# How Can I Contribute?

## Reporting Bugs

For software crashes, display errors, inconsistent application behavior,
please provide the steps to reproduce the error or any other useful information, e.g. display resolution etc.

If you find an error in the calculation of the score,
please try to refer to the corresponding section in the rulebook.

## Suggesting Enhancements

Any suggestions are welcome.
Enhancement suggestions are tracked as GitHub issues.

An additional description of why you think your proposal is an enhancement is of great value.

## Pull Requests

Please respect these prerequisites to have your contribution considered by the maintainers:

After applying your changes the app should still **build** and all **tests must pass**.
At least the following commands must run without errors:

    ./gradlew ... # TODO 
    ./gradlew build check

Pull request must pass the build on [travis ci](https://travis-ci.org/raphaelmeyer/caverna).

While the prerequisites above must be satisfied prior to having your pull request reviewed,
the reviewer may ask you to complete additional design work, tests,
or other changes before your pull request can be ultimately accepted.
