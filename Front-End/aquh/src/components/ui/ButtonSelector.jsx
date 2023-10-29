import classes from "./ButtonSelector.module.css"
import { useState } from "react"

export default function ButtonSelector({
    options = [],
    onSelect,
    variant,
    initiallySelected
}) {

    const [selectedOption, setSelectedOption] = useState(initiallySelected);

    let variantClass = classes[variant] ?? classes.regular;

    const onOptionClick = (option) => {
        onSelect?.(option)
        setSelectedOption(option)
    }

    const buttons = options?.map(option => 
        <button
            key={option}
            onClick={() => onOptionClick(option)}
            className={variantClass + " " + (selectedOption === option ? classes.selected : "")}>
            { option }
        </button>
    )

    return <div className={classes.container}>
        { buttons }
    </div>
}