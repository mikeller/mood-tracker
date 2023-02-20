export type MoodResults = {
    date: string;
    moods: Map<string, number>;
    verbatims: Map<string, Array<string>>;
};

export type MoodValues = Map<string, string>;