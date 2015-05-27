# summarizer

A simple text summarizer software and library written in Clojure

## Installation

No installation needed. Just clone the git repository.

## Usage


    $ java -jar summarizer-0.1.0-standalone.jar text-file-to-summarize amount-of-summarization

amount-of-summarization is a value between 0 and 1.
Or you can use lein to run the summarizer as follows
    $ lein run text-file-to-summarize amount-of-summarization

## Examples

    $ lein run textsample.txt 0.5


## License

Copyright © 2015

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
