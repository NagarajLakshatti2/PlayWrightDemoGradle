# Free APIs and Libraries Used in This Project

This document details all the free, open-source APIs and libraries used in the AI Learning Project, along with practical code examples.

## 📋 Table of Contents

1. [Hugging Face Transformers](#1-hugging-face-transformers)
2. [ChromaDB](#2-chromadb)
3. [Sentence Transformers](#3-sentence-transformers)
4. [PyTorch](#4-pytorch)
5. [Scikit-learn](#5-scikit-learn)
6. [NumPy](#6-numpy)
7. [Pandas](#7-pandas)
8. [LangChain](#8-langchain)
9. [LangGraph](#9-langgraph)

---

## 1. Hugging Face Transformers

### 📌 What It Is
Hugging Face provides free access to thousands of pre-trained AI models including LLMs, image models, audio models, and more.

### 🆓 Free Features
- **Free Models**: GPT-2, BERT, T5, and many others
- **Free Inference**: Run models locally without API calls
- **No API Key Required**: For basic usage
- **Model Hub**: Access to 100,000+ free models

### 💰 Pricing
- **Free**: Local model inference
- **Free**: Model downloads from Hugging Face Hub
- **Paid**: Inference API (optional, not used in this project)

### 🔗 Official Website
- https://huggingface.co/
- https://huggingface.co/docs/transformers/

### 📦 Installation
```bash
pip install transformers torch
```

### 💻 Practical Usage in This Project

#### Text Generation
```python
from transformers import AutoModelForCausalLM, AutoTokenizer

# Load free GPT-2 model
model_name = "gpt2"
tokenizer = AutoTokenizer.from_pretrained(model_name)
model = AutoModelForCausalLM.from_pretrained(model_name)

# Generate text
input_text = "The future of AI is"
inputs = tokenizer(input_text, return_tensors="pt")

outputs = model.generate(
    inputs["input_ids"],
    max_length=100,
    temperature=0.7,
    do_sample=True
)

result = tokenizer.decode(outputs[0], skip_special_tokens=True)
print(result)
```

#### Text Classification
```python
from transformers import pipeline

# Free sentiment analysis pipeline
classifier = pipeline("sentiment-analysis")
result = classifier("I love learning about AI!")
print(result)
# Output: [{'label': 'POSITIVE', 'score': 0.9998}]
```

#### Question Answering
```python
# Free QA pipeline
qa = pipeline("question-answering")
result = qa(
    question="What is AI?",
    context="AI is artificial intelligence"
)
print(result)
```

### 📁 Used In
- `modules/llm/llm_manager.py` - Text generation and classification
- `docs/02_llm.md` - LLM documentation

### ⚠️ Limitations
- Large models require significant RAM/VRAM
- No internet access required after download
- Model size can be several GB

### 🔄 Alternatives
- OpenAI API (paid)
- Anthropic Claude API (paid)
- Local LLaMA models (free, larger)

---

## 2. ChromaDB

### 📌 What It Is
ChromaDB is a free, open-source vector database for storing and searching embeddings.

### 🆓 Free Features
- **Completely Free**: Open-source MIT license
- **Local Storage**: No cloud costs
- **Unlimited Storage**: Based on your disk space
- **No API Key Required**

### 💰 Pricing
- **100% Free**: Self-hosted, no cloud costs
- **Optional**: Chroma Cloud (paid, not used in this project)

### 🔗 Official Website
- https://www.trychroma.com/
- https://docs.trychroma.com/

### 📦 Installation
```bash
pip install chromadb
```

### 💻 Practical Usage in This Project

#### Basic Vector Store
```python
import chromadb
from chromadb.config import Settings

# Create local client (free)
client = chromadb.Client(Settings(anonymized_trending=False))

# Create collection
collection = client.create_collection("documents")

# Add documents
collection.add(
    documents=["AI is transforming healthcare"],
    embeddings=[[0.1, 0.2, 0.3]],  # Your embeddings
    ids=["doc1"]
)

# Query
results = collection.query(
    query_embeddings=[[0.1, 0.2, 0.3]],
    n_results=1
)
print(results)
```

#### With Sentence Transformers
```python
from sentence_transformers import SentenceTransformer
import chromadb

# Free embedding model
embedder = SentenceTransformer('all-MiniLM-L6-v2')

# Create vector store
client = chromadb.Client()
collection = client.create_collection("documents")

# Add with automatic embeddings
text = "Artificial Intelligence is amazing"
embedding = embedder.encode(text).tolist()

collection.add(
    documents=[text],
    embeddings=[embedding],
    ids=["doc1"]
)

# Search
query = "What is AI?"
query_embedding = embedder.encode(query).tolist()

results = collection.query(
    query_embeddings=[query_embedding],
    n_results=3
)
```

### 📁 Used In
- `modules/rag/rag_system.py` - Vector storage for RAG
- `modules/memory/memory_systems.py` - Vector memory
- `docs/03_rag.md` - RAG documentation

### ⚠️ Limitations
- Self-hosted only (you manage scaling)
- No built-in cloud backup
- Requires manual maintenance

### 🔄 Alternatives
- Pinecone (free tier available)
- Weaviate (open-source)
- FAISS (Meta, open-source)

---

## 3. Sentence Transformers

### 📌 What It Is
Sentence Transformers provides free pre-trained models for creating text embeddings.

### 🆓 Free Features
- **Free Models**: Multiple embedding models
- **No API Key Required**: Run locally
- **High Quality**: State-of-the-art embeddings
- **Multiple Languages**: Support for 50+ languages

### 💰 Pricing
- **100% Free**: Open-source models
- **No Usage Limits**: Run as much as you want

### 🔗 Official Website
- https://www.sbert.net/
- https://github.com/UKPLab/sentence-transformers

### 📦 Installation
```bash
pip install sentence-transformers
```

### 💻 Practical Usage in This Project

#### Basic Embedding
```python
from sentence_transformers import SentenceTransformer

# Load free model (384 dimensions, fast)
model = SentenceTransformer('all-MiniLM-L6-v2')

# Create embeddings
text = "Artificial Intelligence is transforming the world"
embedding = model.encode(text)

print(f"Embedding shape: {embedding.shape}")
print(f"First 5 values: {embedding[:5]}")
```

#### Batch Embeddings
```python
# Embed multiple texts
texts = [
    "AI is amazing",
    "Machine learning is powerful",
    "Deep learning uses neural networks"
]

embeddings = model.encode(texts)
print(f"Batch shape: {embeddings.shape}")
```

#### Similarity Search
```python
# Calculate similarity
text1 = "AI is artificial intelligence"
text2 = "Machine learning is a subset of AI"

emb1 = model.encode(text1)
emb2 = model.encode(text2)

# Cosine similarity
import numpy as np
similarity = np.dot(emb1, emb2) / (np.linalg.norm(emb1) * np.linalg.norm(emb2))
print(f"Similarity: {similarity:.4f}")
```

### 📁 Used In
- `modules/rag/rag_system.py` - Creating embeddings for RAG
- `modules/memory/memory_systems.py` - Vector memory embeddings
- `docs/03_rag.md` - RAG documentation

### ⚠️ Limitations
- Model size: ~100-500MB per model
- Requires RAM for loading models
- Slower than GPU-based solutions

### 🔄 Alternatives
- OpenAI Embeddings (paid)
- Cohere Embeddings (free tier)
- Hugging Face Embeddings (free)

---

## 4. PyTorch

### 📌 What It Is
PyTorch is a free, open-source deep learning framework for building neural networks.

### 🆓 Free Features
- **Completely Free**: Open-source with permissive license
- **No API Key Required**: Run locally
- **GPU Support**: Free CUDA support for NVIDIA GPUs
- **Rich Ecosystem**: Thousands of free libraries

### 💰 Pricing
- **100% Free**: Open-source
- **No Cloud Costs**: Run on your own hardware

### 🔗 Official Website
- https://pytorch.org/
- https://pytorch.org/tutorials/

### 📦 Installation
```bash
pip install torch
```

### 💻 Practical Usage in This Project

#### Simple Neural Network
```python
import torch
import torch.nn as nn

# Define network
class SimpleNN(nn.Module):
    def __init__(self, input_size, hidden_size, output_size):
        super(SimpleNN, self).__init__()
        self.fc1 = nn.Linear(input_size, hidden_size)
        self.relu = nn.ReLU()
        self.fc2 = nn.Linear(hidden_size, output_size)

    def forward(self, x):
        x = self.fc1(x)
        x = self.relu(x)
        x = self.fc2(x)
        return x

# Create model
model = SimpleNN(10, 20, 1)

# Generate data
X = torch.randn(100, 10)
y = torch.randn(100, 1)

# Training
criterion = nn.MSELoss()
optimizer = torch.optim.Adam(model.parameters(), lr=0.01)

for epoch in range(100):
    outputs = model(X)
    loss = criterion(outputs, y)

    optimizer.zero_grad()
    loss.backward()
    optimizer.step()

    if epoch % 20 == 0:
        print(f"Epoch {epoch}, Loss: {loss.item():.4f}")
```

#### CNN for Images
```python
class SimpleCNN(nn.Module):
    def __init__(self):
        super(SimpleCNN, self).__init__()
        self.conv1 = nn.Conv2d(1, 16, kernel_size=3)
        self.conv2 = nn.Conv2d(16, 32, kernel_size=3)
        self.pool = nn.MaxPool2d(2, 2)
        self.fc1 = nn.Linear(32 * 5 * 5, 128)
        self.fc2 = nn.Linear(128, 10)

    def forward(self, x):
        x = self.pool(torch.relu(self.conv1(x)))
        x = self.pool(torch.relu(self.conv2(x)))
        x = x.view(-1, 32 * 5 * 5)
        x = torch.relu(self.fc1(x))
        x = self.fc2(x)
        return x
```

### 📁 Used In
- `modules/basics/dl_basics.py` - Deep learning examples
- `docs/01_basics.md` - DL documentation

### ⚠️ Limitations
- Requires significant RAM/VRAM for large models
- Learning curve for beginners
- CUDA requires NVIDIA GPU

### 🔄 Alternatives
- TensorFlow (free)
- JAX (free)
- Keras (free, built on TensorFlow)

---

## 5. Scikit-learn

### 📌 What It Is
Scikit-learn is a free machine learning library for traditional ML algorithms.

### 🆓 Free Features
- **Completely Free**: Open-source (BSD license)
- **No API Key Required**: Run locally
- **Rich Algorithms**: 50+ ML algorithms
- **Well Documented**: Extensive tutorials

### 💰 Pricing
- **100% Free**: Open-source
- **No Usage Limits**

### 🔗 Official Website
- https://scikit-learn.org/
- https://scikit-learn.org/stable/user_guide.html

### 📦 Installation
```bash
pip install scikit-learn
```

### 💻 Practical Usage in This Project

#### Linear Regression
```python
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
import numpy as np

# Generate data
X = np.random.rand(100, 1) * 10
y = 2 * X + 1 + np.random.randn(100, 1) * 0.5

# Split data
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

# Train model
model = LinearRegression()
model.fit(X_train, y_train)

# Predict
y_pred = model.predict(X_test)

# Evaluate
mse = mean_squared_error(y_test, y_pred)
print(f"MSE: {mse:.4f}")
print(f"Coefficients: {model.coef_}")
```

#### Classification
```python
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score

# Binary classification
X = np.random.randn(100, 2)
y = (X[:, 0] + X[:, 1] > 0).astype(int)

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

model = LogisticRegression()
model.fit(X_train, y_train)

y_pred = model.predict(X_test)
accuracy = accuracy_score(y_test, y_pred)
print(f"Accuracy: {accuracy:.4f}")
```

#### Clustering
```python
from sklearn.cluster import KMeans

# Generate clusters
X = np.random.randn(100, 2)
X[:50] += 2
X[50:] -= 2

# Cluster
model = KMeans(n_clusters=2, random_state=42)
labels = model.fit_predict(X)

print(f"Cluster centers: {model.cluster_centers_}")
print(f"Labels: {np.bincount(labels)}")
```

### 📁 Used In
- `modules/basics/ml_basics.py` - Traditional ML examples
- `docs/01_basics.md` - ML documentation

### ⚠️ Limitations
- Not suitable for deep learning
- Limited GPU support
- Traditional ML only

### 🔄 Alternatives
- XGBoost (free)
- LightGBM (free)
- CatBoost (free)

---

## 6. NumPy

### 📌 What It Is
NumPy is a free library for numerical computing in Python.

### 🆓 Free Features
- **Completely Free**: Open-source
- **Fast**: Optimized C implementation
- **Essential**: Foundation for most ML libraries
- **No API Key Required**

### 💰 Pricing
- **100% Free**: Open-source

### 🔗 Official Website
- https://numpy.org/
- https://numpy.org/doc/

### 📦 Installation
```bash
pip install numpy
```

### 💻 Practical Usage in This Project

#### Array Operations
```python
import numpy as np

# Create arrays
arr = np.array([1, 2, 3, 4, 5])
matrix = np.random.randn(3, 3)

# Operations
print(f"Mean: {arr.mean()}")
print(f"Sum: {arr.sum()}")
print(f"Matrix shape: {matrix.shape}")
```

#### Linear Algebra
```python
# Matrix multiplication
A = np.random.randn(2, 3)
B = np.random.randn(3, 2)
C = np.dot(A, B)

# Eigenvalues
eigenvalues = np.linalg.eigvals(matrix)
```

### 📁 Used In
- All modules - Numerical operations
- `modules/basics/` - ML/DL implementations

### ⚠️ Limitations
- Not for symbolic math (use SymPy)
- Memory intensive for large arrays

### 🔄 Alternatives
- Pandas (for tabular data)
- SciPy (for scientific computing)

---

## 7. Pandas

### 📌 What It Is
Pandas is a free library for data manipulation and analysis.

### 🆓 Free Features
- **Completely Free**: Open-source
- **Powerful**: DataFrames for structured data
- **I/O Support**: Read/write many formats
- **No API Key Required**

### 💰 Pricing
- **100% Free**: Open-source

### 🔗 Official Website
- https://pandas.pydata.org/
- https://pandas.pydata.org/docs/

### 📦 Installation
```bash
pip install pandas
```

### 💻 Practical Usage in This Project

#### DataFrame Operations
```python
import pandas as pd

# Create DataFrame
data = {
    'name': ['Alice', 'Bob', 'Charlie'],
    'age': [25, 30, 35],
    'city': ['NYC', 'LA', 'Chicago']
}
df = pd.DataFrame(data)

# Operations
print(df.head())
print(df.describe())
print(df['age'].mean())
```

### 📁 Used In
- Data processing examples
- Document handling

### ⚠️ Limitations
- Memory intensive for large datasets
- Not for real-time streaming

### 🔄 Alternatives
- Polars (faster, free)
- Dask (for big data, free)

---

## 8. LangChain

### 📌 What It Is
LangChain is a free framework for building applications with LLMs.

### 🆓 Free Features
- **Open-Source**: Free to use
- **No API Key Required**: For local models
- **Rich Integrations**: Works with many free APIs
- **Agent Framework**: Build AI agents

### 💰 Pricing
- **Free**: Open-source core
- **Paid**: LangSmith (tracing, optional)

### 🔗 Official Website
- https://python.langchain.com/
- https://github.com/langchain-ai/langchain

### 📦 Installation
```bash
pip install langchain langchain-community
```

### 💻 Practical Usage in This Project

#### Basic Chain
```python
from langchain.chains import LLMChain
from langchain.prompts import PromptTemplate

# Create prompt
prompt = PromptTemplate(
    input_variables=["question"],
    template="Answer: {question}"
)

# Create chain (with local LLM)
chain = LLMChain(llm=local_llm, prompt=prompt)
result = chain.run("What is AI?")
```

### 📁 Used In
- Agent implementations
- Documentation references

### ⚠️ Limitations
- Requires LLM (can use free local models)
- Complex API for beginners

### 🔄 Alternatives
- LlamaIndex (free)
- Semantic Kernel (free)

---

## 9. LangGraph

### 📌 What It Is
LangGraph is a free framework for building stateful, multi-actor applications with LLMs.

### 🆓 Free Features
- **Open-Source**: Free to use
- **State Management**: Built-in state handling
- **Workflow Orchestration**: Define complex workflows
- **No API Key Required**: For local models

### 💰 Pricing
- **100% Free**: Open-source

### 🔗 Official Website
- https://langchain-ai.github.io/langgraph/
- https://github.com/langchain-ai/langgraph

### 📦 Installation
```bash
pip install langgraph
```

### 💻 Practical Usage in This Project

#### Simple Workflow
```python
from langgraph.graph import StateGraph, END

# Define state
class AgentState(TypedDict):
    messages: list
    current_step: str

# Define nodes
def research_node(state):
    # Research logic
    return {"messages": state["messages"] + ["Research done"]}

def coding_node(state):
    # Coding logic
    return {"messages": state["messages"] + ["Code written"]}

# Build graph
workflow = StateGraph(AgentState)
workflow.add_node("research", research_node)
workflow.add_node("coding", coding_node)

workflow.set_entry_point("research")
workflow.add_edge("research", "coding")
workflow.add_edge("coding", END)

# Compile
app = workflow.compile()
```

### 📁 Used In
- Multi-agent orchestration
- Workflow definitions

### ⚠️ Limitations
- Requires LangChain
- Learning curve

### 🔄 Alternatives
- AutoGen (Microsoft, free)
- CrewAI (free)

---

## 📊 Summary Table

| API/Library | Type | Cost | API Key Required | Used For |
|-------------|------|------|------------------|----------|
| Hugging Face Transformers | ML Models | Free | No | LLM inference |
| ChromaDB | Vector DB | Free | No | RAG storage |
| Sentence Transformers | Embeddings | Free | No | Text embeddings |
| PyTorch | DL Framework | Free | No | Neural networks |
| Scikit-learn | ML Library | Free | No | Traditional ML |
| NumPy | Numerical Computing | Free | No | Array operations |
| Pandas | Data Manipulation | Free | No | Data processing |
| LangChain | LLM Framework | Free | No | Agent building |
| LangGraph | Workflow Framework | Free | No | Orchestration |

## 🎯 How to Use These APIs

### Step 1: Install Dependencies
```bash
pip install -r requirements.txt
```

### Step 2: Run Examples
```bash
# Run any module directly
python -m modules.basics.ml_basics
python -m modules.rag.rag_system
python -m modules.single_agent.agent
```

### Step 3: No API Keys Needed
All APIs used in this project are **free and don't require API keys** for basic usage. Just install and run!

## 💡 Tips for Free APIs

1. **Local First**: All these APIs run locally - no cloud costs
2. **No Limits**: Use as much as you want
3. **Privacy**: Your data stays on your machine
4. **Offline**: Works without internet after initial download
5. **Extendable**: Easy to add paid APIs later if needed

## 🔧 Adding Paid APIs (Optional)

If you want to add paid APIs later:

### OpenAI
```python
# Add to .env
OPENAI_API_KEY=your_key_here

# Use in code
from langchain_openai import ChatOpenAI
llm = ChatOpenAI(api_key=os.getenv("OPENAI_API_KEY"))
```

### Anthropic
```python
# Add to .env
ANTHROPIC_API_KEY=your_key_here

# Use in code
from langchain_anthropic import ChatAnthropic
llm = ChatAnthropic(api_key=os.getenv("ANTHROPIC_API_KEY"))
```

## 📚 Additional Resources

- **Hugging Face Model Hub**: https://huggingface.co/models
- **ChromaDB Documentation**: https://docs.trychroma.com/
- **PyTorch Tutorials**: https://pytorch.org/tutorials/
- **Scikit-learn User Guide**: https://scikit-learn.org/stable/user_guide.html
- **LangChain Documentation**: https://python.langchain.com/

---

## ✅ Conclusion

This project uses **100% free, open-source APIs and libraries**. No API keys are required for the core functionality. All tools run locally on your machine, giving you:

- ✅ No monthly costs
- ✅ No usage limits
- ✅ Complete privacy
- ✅ Offline capability
- ✅ Full control

You can learn and build production-ready AI systems without spending a dime! 🚀
