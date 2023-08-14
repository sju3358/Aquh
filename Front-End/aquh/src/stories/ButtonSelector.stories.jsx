import ButtonSelector from "../components/ui/ButtonSelector";

export default {
  title: 'UI/ButtonSelector',
  component: ButtonSelector
};

export const Regular = {
    render: () =>
        <ButtonSelector
            variant="regular"
            initiallySelected={"Gimbap"}
            options={["Apple", "Gimbap", "Samgyeopsal", "Tteokbokki", "Bibimbap", "Kimchi"]} />
};

export const Alternate = {
    render: () =>
        <ButtonSelector
            variant="alternate"
            initiallySelected={"Samgyeopsal"}
            options={["Apple", "Gimbap", "Samgyeopsal", "Tteokbokki", "Bibimbap", "Kimchi"]} />
};