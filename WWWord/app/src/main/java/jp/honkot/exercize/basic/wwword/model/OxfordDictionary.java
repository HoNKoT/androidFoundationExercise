package jp.honkot.exercize.basic.wwword.model;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Table
public class OxfordDictionary extends BaseModel {

    @Column
    private String rawJson;

    @Column
    private String word;

    public MetaData metaData;
    public ArrayList<Result> results;

    public class MetaData {
        public String provider;

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("MetaData{");
            sb.append("provider='").append(provider).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public class Result {
        private final static String ID = "id";
        private final static String LANGUAGE = "language";
        private final static String TYPE = "type";
        private final static String WORD = "word";
        private final static String LEXICAL_ENTRIES = "lexicalEntries";

        public String id;
        public String language;
        public String type;
        public String word;

        public ArrayList<LexicalEntry> lexicalEntries;

        private Result(JSONObject json) {
            try {
                if (!json.isNull(ID)) id = json.getString(ID);
                if (!json.isNull(LANGUAGE)) language = json.getString(LANGUAGE);
                if (!json.isNull(TYPE)) type = json.getString(TYPE);
                if (!json.isNull(WORD)) word = json.getString(WORD);

                JSONArray lexicalEntriesArray = json.getJSONArray(LEXICAL_ENTRIES);
                lexicalEntries = new ArrayList<>();
                for (int i = 0; i < lexicalEntriesArray.length(); i++) {
                    JSONObject lexicalEntryJson = lexicalEntriesArray.getJSONObject(i);
                    LexicalEntry entry = new LexicalEntry(lexicalEntryJson);
                    lexicalEntries.add(entry);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Results{");
            sb.append("id='").append(id).append('\'');
            sb.append(", language='").append(language).append('\'');
            sb.append(", type='").append(type).append('\'');
            sb.append(", word='").append(word).append('\'');
            sb.append(", lexicalEntries=").append(lexicalEntries);
            sb.append('}');
            return sb.toString();
        }
    }

    public class LexicalEntry {
        private final static String ENTRIES = "entries";
        private final static String LANGUAGE = "language";
        private final static String LEXICAL_CATEGORY = "lexicalCategory";
        private final static String PRONUNCIATIONS = "pronunciations";
        private final static String TEXT = "text";

        public ArrayList<Entry> entries;
        public String language;
        public String lexicalCategory;
        public ArrayList<Pronunciation> pronunciations;
        public String text;

        private LexicalEntry(JSONObject json) {
            try {
                if (!json.isNull(LANGUAGE)) language = json.getString(LANGUAGE);
                if (!json.isNull(LEXICAL_CATEGORY)) lexicalCategory = json.getString(LEXICAL_CATEGORY);
                if (!json.isNull(TEXT)) text = json.getString(TEXT);

                JSONArray entriesArray = json.getJSONArray(ENTRIES);
                entries = new ArrayList<>();
                for (int i = 0; i < entriesArray.length(); i++) {
                    JSONObject lexicalEntryJson = entriesArray.getJSONObject(i);
                    Entry entry = new Entry(lexicalEntryJson);
                    entries.add(entry);
                }

                JSONArray pronunciationsArray = json.getJSONArray(PRONUNCIATIONS);
                pronunciations = new ArrayList<>();
                for (int i = 0; i < pronunciationsArray.length(); i++) {
                    JSONObject pronunciationJson = pronunciationsArray.getJSONObject(i);
                    Pronunciation pronunciation = new Pronunciation(pronunciationJson);
                    pronunciations.add(pronunciation);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("LexicalEntry{");
            sb.append("entries=").append(entries);
            sb.append(", language='").append(language).append('\'');
            sb.append(", lexicalCategory='").append(lexicalCategory).append('\'');
            sb.append(", pronunciations=").append(pronunciations);
            sb.append(", text='").append(text).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public class Entry {
        private final static String ETYMOLOGIES = "etymologies";
        private final static String GRAMMATICAL_FEATURES = "grammaticalFeatures";
        private final static String HOMOGRAPH_NUMBER = "homographNumber";
        private final static String SENSES = "senses";

        public ArrayList<String> etymologies;
        public ArrayList<GrammaticalFeatures> grammaticalFeatures;
        public String homographNumber;
        public ArrayList<Sense> senses;

        private Entry(JSONObject json) {
            try {
                JSONArray etymologiesArray = json.getJSONArray(ETYMOLOGIES);
                etymologies = new ArrayList<>();
                for (int i = 0; i < etymologiesArray.length(); i++) {
                    etymologies.add(etymologiesArray.getString(i));
                }

                JSONArray grammaticalFeaturesArray = json.getJSONArray(GRAMMATICAL_FEATURES);
                grammaticalFeatures = new ArrayList<>();
                for (int i = 0; i < grammaticalFeaturesArray.length(); i++) {
                    JSONObject grammaticalFeatureJson = grammaticalFeaturesArray.getJSONObject(i);
                    GrammaticalFeatures grammaticalFeature = new GrammaticalFeatures(grammaticalFeatureJson);
                    grammaticalFeatures.add(grammaticalFeature);
                }

                if (!json.isNull(HOMOGRAPH_NUMBER)) homographNumber = json.getString(HOMOGRAPH_NUMBER);

                JSONArray sensesArray = json.getJSONArray(SENSES);
                senses = new ArrayList<>();
                for (int i = 0; i < sensesArray.length(); i++) {
                    JSONObject senseJson = sensesArray.getJSONObject(i);
                    Sense sense = new Sense(senseJson);
                    senses.add(sense);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Entry{");
            sb.append("etymologies=").append(etymologies);
            sb.append(", grammaticalFeatures=").append(grammaticalFeatures);
            sb.append(", homographNumber='").append(homographNumber).append('\'');
            sb.append(", senses=").append(senses);
            sb.append('}');
            return sb.toString();
        }
    }

    public class Pronunciation {
        private final static String AUDIO_FILE = "audioFile";
        private final static String DIALECTS = "grammaticalFeatures";
        private final static String PHONETIC_NOTATION = "homographNumber";
        private final static String PHONETIC_SPELLING = "senses";

        public String audioFile;
        public ArrayList<String> dialects;
        public String phoneticNotation;
        public String phoneticSpelling;

        private Pronunciation(JSONObject json) {
            try {

                if (!json.isNull(AUDIO_FILE)) audioFile = json.getString(AUDIO_FILE);
                if (!json.isNull(PHONETIC_NOTATION)) phoneticNotation = json.getString(PHONETIC_NOTATION);
                if (!json.isNull(PHONETIC_SPELLING)) phoneticSpelling = json.getString(PHONETIC_SPELLING);

                JSONArray dialectsArray = json.getJSONArray(DIALECTS);
                dialects = new ArrayList<>();
                for (int i = 0; i < dialectsArray.length(); i++) {
                    dialects.add(dialectsArray.getString(i));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Pronunciation{");
            sb.append("audioFile='").append(audioFile).append('\'');
            sb.append(", dialects=").append(dialects);
            sb.append(", phoneticNotation='").append(phoneticNotation).append('\'');
            sb.append(", phoneticSpelling='").append(phoneticSpelling).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public class GrammaticalFeatures {
        private final static String TEXT = "text";
        private final static String TYPE = "type";

        public String text;
        public String type;

        private GrammaticalFeatures(JSONObject json) {
            try {
                if (!json.isNull(TEXT)) text = json.getString(TEXT);
                if (!json.isNull(TYPE)) type = json.getString(TYPE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("GrammaticalFeatures{");
            sb.append("text='").append(text).append('\'');
            sb.append(", type='").append(type).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public class Sense {
        private final static String DEFINITIONS = "definitions";
        private final static String EXAMPLES = "examples";
        private final static String ID = "id";
        private final static String DOMAINS = "domains";
        private final static String SUBSENCES = "subSenses";

        public ArrayList<String> definitions;
        public ArrayList<Example> examples;
        public String id;
        public ArrayList<String> domains;
        public ArrayList<Sense> subSenses;

        private Sense(JSONObject json) {
            try {
                JSONArray definitionsArray = json.getJSONArray(DEFINITIONS);
                definitions = new ArrayList<>();
                for (int i = 0; i < definitionsArray.length(); i++) {
                    definitions.add(definitionsArray.getString(i));
                }

                if (!json.isNull(ID)) id = json.getString(ID);

                JSONArray examplesArray = json.getJSONArray(EXAMPLES);
                examples = new ArrayList<>();
                for (int i = 0; i < examplesArray.length(); i++) {
                    JSONObject exampleJson = examplesArray.getJSONObject(i);
                    Example example = new Example(exampleJson);
                    examples.add(example);
                }

                JSONArray domainsArray = json.getJSONArray(DOMAINS);
                domains = new ArrayList<>();
                for (int i = 0; i < domainsArray.length(); i++) {
                    domains.add(domainsArray.getString(i));
                }

                JSONArray subSensesArray = json.getJSONArray(SUBSENCES);
                subSenses = new ArrayList<>();
                for (int i = 0; i < subSensesArray.length(); i++) {
                    JSONObject senseJson = subSensesArray.getJSONObject(i);
                    Sense sense = new Sense(senseJson);
                    subSenses.add(sense);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Sense{");
            sb.append("definitions=").append(definitions);
            sb.append(", examples=").append(examples);
            sb.append(", id='").append(id).append('\'');
            sb.append(", domains=").append(domains);
            sb.append(", subSenses=").append(subSenses);
            sb.append('}');
            return sb.toString();
        }
    }

    public class Example {
        private final static String TEXT = "text";

        public String text;

        private Example(JSONObject json) {
            try {
                if (!json.isNull(TEXT)) text = json.getString(TEXT);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Example{");
            sb.append("text='").append(text).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OxfordDictionary{");
        sb.append("metaData=").append(metaData);
        sb.append(", results=").append(results);
        sb.append('}');
        return sb.toString();
    }

    public static OxfordDictionary getInstance(JSONObject json) {
        OxfordDictionary dic = new OxfordDictionary();
        try {
            dic.parseFromJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dic;
    }

    private void parseFromJson(JSONObject json) throws JSONException {
        JSONObject metadata = json.getJSONObject("metadata");

        // MetaData
        this.metaData = new MetaData();
        this.metaData.provider = metadata.getString("provider");

        // Result
        this.results = new ArrayList<>();
        JSONArray resultArray = json.getJSONArray("results");
        for (int i = 0; i < resultArray.length(); i++) {
            JSONObject resultJson = resultArray.getJSONObject(i);
            Result result = new Result(resultJson);
            this.results.add(result);
        }
    }
}
