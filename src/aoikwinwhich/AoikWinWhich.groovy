//
package aoikwinwhich
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

def find_executable(prog) {
    // 8f1kRCu
    def env_var_PATHEXT = System.getenv("PATHEXT")
    /// can be null

    // 6qhHTHF
    // split into a list of extensions
    def ext_s = (env_var_PATHEXT == null) ? [] :
        env_var_PATHEXT.split(File.pathSeparator).toList()

    // 2pGJrMW
    // strip
    ext_s = ext_s.collect({it.trim()})

    // 2gqeHHl
    // remove empty
    ext_s = ext_s.grep({!it.equals("")})

    // 2zdGM8W
    // convert to lowercase
    ext_s = ext_s.collect({it.toLowerCase()})

    // 2fT8aRB
    // uniquify
    ext_s.unique()

    // 4ysaQVN
    def env_var_PATH = System.getenv("PATH")
    /// can be null

    // 6mPI0lg
    def dir_path_s = (env_var_PATH == null) ? [] :
        env_var_PATH.split(File.pathSeparator).toList()

    // 5rT49zI
    // insert empty dir path to the beginning
    //
    // Empty dir handles the case that |prog| is a path, either relative or
    //  absolute. See code 7rO7NIN.
    dir_path_s.add(0, "")

    // 2klTv20
    // uniquify
    dir_path_s.unique()
    /// LinkedHashSet keeps the original order.

    // 6bFwhbv
    def exe_path_s = []

    for (dir_path in dir_path_s) {
        // 7rO7NIN
        // synthesize a path with the dir and prog
        def path = dir_path.equals("") ? prog :
            Paths.get(dir_path, prog).toString()

        // 6kZa5cq
        // assume the path has extension, check if it is an executable
        if (ext_s.any({path.endsWith(it)})) {
            if (Files.isRegularFile(Paths.get(path))) {
                exe_path_s.add(path)
            }
        }

        // 2sJhhEV
        // assume the path has no extension
        for (ext in ext_s) {
            // 6k9X6GP
            // synthesize a new path with the path and the executable extension
            def path_plus_ext = path + ext

            // 6kabzQg
            // check if it is an executable
            if (Files.isRegularFile(Paths.get(path_plus_ext))) {
                exe_path_s.add(path_plus_ext)
            }
        }
    }

    //
    return exe_path_s
}

def main2(args) {
    // 9mlJlKg
    // check if one cmd arg is given
    if (args.length != 1) {
        // 7rOUXFo
        // print program usage
        println(/Usage: aoikwinwhich PROG/)
        println("")
        println(/#\/ PROG can be either name or path/)
        println(/aoikwinwhich notepad.exe/)
        println(/aoikwinwhich C:\Windows\notepad.exe/)
        println("")
        println(/#\/ PROG can be either absolute or relative/)
        println(/aoikwinwhich C:\Windows\notepad.exe/)
        println(/aoikwinwhich Windows\\notepad.exe/)
        println("")
        println(/#\/ PROG can be either with or without extension/)
        println(/aoikwinwhich notepad.exe/)
        println(/aoikwinwhich notepad/)
        println(/aoikwinwhich C:\Windows\notepad.exe/)
        println(/aoikwinwhich C:\Windows\notepad/)

        // 3nqHnP7
        return
    }

    // 9m5B08H
    // get name or path of a program from cmd arg
    def prog = args[0]

    // 8ulvPXM
    // find executables
    def path_s = find_executable(prog)

    // 5fWrcaF
    // has found none, exit
    if (path_s.size() == 0) {
        // 3uswpx0
        return
    }

    // 9xPCWuS
    // has found some, output
    def txt = path_s.join("\n")

    println(txt)

    //
    return
}

//
main2(args)
/// |args| is func arg of the wrapping |main| func auto created by Groovy.
