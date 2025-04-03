import { useRunCode } from "@/context/RunCodeContext"
import useResponsive from "@/hooks/useResponsive"
import { ChangeEvent } from "react"
import toast from "react-hot-toast"
import { LuCopy } from "react-icons/lu"
import { PiCaretDownBold } from "react-icons/pi"

function RunView() {
    const { viewHeight } = useResponsive()
    const {
        setInput,
        output,
        isRunning,
        supportedLanguages,
        selectedLanguage,
        setSelectedLanguage,
        runCode,
    } = useRunCode()

    const handleLanguageChange = (e: ChangeEvent<HTMLSelectElement>) => {
        const lang = JSON.parse(e.target.value)
        setSelectedLanguage(lang)
    }

    const copyOutput = () => {
        navigator.clipboard.writeText(output)
        toast.success("Output copied to clipboard")
    }

    return (
        <div
            className="flex flex-col items-center gap-2 p-4"
            style={{ height: viewHeight }}
        >
            <h1 className="view-title">Run Code</h1>
            <div className="flex h-[90%] w-full flex-col items-end gap-2 md:h-[92%]">
                <div className="relative w-full">
                    <select
                        className="rounded-md border bg-transparent  px-4 py-2 text-white outline-none
                        bg-[#8e51ff]  font-medium  decoration-1 
                     hover:bg-[#8ec5ff] hover:text-black transition-colors duration-200
                     focus:outline-none focus:ring-2 focus:ring-[#861043] focus:ring-offset-2
                     active:bg-[#1c398e]
                        "
                        value={JSON.stringify(selectedLanguage)}
                        onChange={handleLanguageChange}
                    >
                        {supportedLanguages
                            .sort((a, b) => (a.language > b.language ? 1 : -1))
                            .map((lang, i) => {
                                return (
                                    <option
                                        key={i}
                                        value={JSON.stringify(lang)}
                                    >
                                        {lang.language +
                                            (lang.version
                                                ? ` (${lang.version})`
                                                : "")}
                                    </option>
                                )
                            })}
                    </select>
                    <PiCaretDownBold
                        size={16}
                        className="absolute bottom-3 right-4 z-10 text-white"
                    />
                </div>
                <textarea
                    className="min-h-[120px] w-full rounded-md border border-white bg-transparent p-2 text-white outline-none
                    focus:outline-none focus:border-[#8e51ff] focus:border-2 focus:ring-2 focus:ring-[#8e51ff]/20
                    "
                    placeholder="Write you input here..."
                    onChange={(e) => setInput(e.target.value)}
                />
                <button
                  className="mt-2 text-center py-2 px-4 rounded-md w-full
                  bg-[#8e51ff] text-white font-medium  decoration-1 
                  hover:bg-[#8ec5ff] transition-colors duration-200
                  focus:outline-none focus:ring-2 focus:ring-[#861043] focus:ring-offset-2
                  active:bg-[#1c398e]"       
                    onClick={runCode}
                    disabled={isRunning}
                >
                    Run
                </button>
                <label className="flex w-full justify-between">
                    Output :
                    <button onClick={copyOutput} title="Copy Output">
                        <LuCopy
                            size={18}
                            className="cursor-pointer text-white"
                        />
                    </button>
                </label>
                <div className="w-full flex-grow resize-none overflow-y-auto rounded-md border-none bg-darkHover p-2 text-white outline-none">
                    <code>
                        <pre className="text-wrap">{output}</pre>
                    </code>
                </div>
            </div>
        </div>
    )
}

export default RunView
